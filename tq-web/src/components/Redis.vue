<template>
    <el-container>
      <el-aside width="400px"></el-aside>
      <el-main>
           <el-table
            :data="items"
            border
            stripe
            style="width: 1051px">
            <el-table-column
              prop="id"
              label="id"
              align="center"
              width="120">
            </el-table-column>
            <el-table-column
              prop="name"
              label="名称"
              align="center"
              width="120">
            </el-table-column>
            <el-table-column
              prop="redisHost"
              label="主机"
              align="center"              
              width="120">
            </el-table-column>
            <el-table-column
              prop="redisPort"
              label="端口"
              align="center"              
              width="120">
            </el-table-column>
            <el-table-column
              prop="redisMode"
              label="模式"
              align="center"              
              width="150">
            </el-table-column>            
            <el-table-column 
                prop="isAlive"
                label="状态" 
                align="center"
                width="120" 
                sortable>
                  <template slot-scope="scope">
                      <el-tag :type="scope.row.isAlive | statusFilter">{{scope.row.isAlive | formatStata}}</el-tag>
                  </template>
            </el-table-column>            
            <el-table-column
              prop="date"
              label="时间"
              align="center"              
              width="180">
            </el-table-column>
            <el-table-column
              label="操作"
              align="center"              
              width="120">
              <template slot-scope="scope">
                <el-button :disabled="scope.row.isAlive" @click="handleClick(scope.row)" type="text" size="small" >启动</el-button>
                <el-button :disabled="!scope.row.isAlive" @click="handleClick(scope.row)" type="text" size="small">停止</el-button>
              </template>
            </el-table-column>
          </el-table>    
      </el-main>
    </el-container>
</template>

<script>
    
import qs from 'qs'
export default {
    name: 'Redis',
    data(){
        return {
          items:[]          
        }
    },
    
    beforeMount() {
        var self = this;
        setInterval(myajax, 10000);
        function myajax() { 
            self.$http.get("/tqmanager/redis/health").then(function(res){
              console.log(res.data.data)
              self.items = res.data.data.health;
            }).catch(function(err){
              console.log(err)
            })
            }  
        myajax();
    },
    
    methods:{
        handleClick(redis) {
            var self = this;
            var data = {
                opertion : redis.isAlive? 0:1,
                id : redis.id
            };
            var param = qs.stringify(data);
            console.log("operation is " + param);
            var params = new URLSearchParams();
            params.append("opertion", redis.isAlive? 0:1);
            params.append("id",redis.id);
            self.$http.post("/tqmanager/redis/task", params).then(function(res){
              console.log(res.data.rdesc)
            }).catch(function(err){
              console.log(err)
            })
        }
    },
    filters: {
        // el-tag类型转换
        statusFilter(status) {
          const statusMap = {
            true: 'success',
            false: 'danger'
          }
          return statusMap[status]
        },
        // 状态显示转换
        formatStata(status) {
          const statusMap = {
            true: '运行',
            false: '宕机'
          }
          return statusMap[status]
        }
      }        
    }

</script>

<style>
    /*重要!否则表格不能正常显示*/
    td {
        background-clip: padding-box;
    }
    /*@import url("../css/base.css");*/
    /*@import url("../css/redis.css");*/
</style>