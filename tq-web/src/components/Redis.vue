<template>
    <el-container>
      <el-aside width="400px"></el-aside>
      <el-main>
           <el-button type="primary" @click="dialogFormVisible = true">添加服务器</el-button>
           <el-table
            :data="items"
            stripe
            @row-dblclick="detailInfo"
            style="width: 1081px">
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-form label-position="left" inline class="demo-table-expand">

                  <el-form-item label="服务器用户名">
                    <span>{{ props.row.serverUsername }}</span>
                  </el-form-item>
                   <el-form-item label="服务器密码">
                    <span>{{ props.row.serverPassword }}</span>
                  </el-form-item>                 
                  <el-form-item label="redis程序目录">
                    <span>{{ props.row.redisBinPath }}</span>
                  </el-form-item>
                  <el-form-item label="redis配置目录">
                    <span>{{ props.row.redisConfigPath }}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>    
            
            <el-table-column
              prop="id"
              label="id"
              align="center"
              width="150">
            </el-table-column>
            <el-table-column
              prop="name"
              label="名称"
              align="center"
              width="150">
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
              label="服务器操作"
              align="center"              
              width="120">
              <template slot-scope="scope">
                <el-button :disabled="scope.row.isAlive" @click="handleClick(scope.row)" type="text" size="small" >启动</el-button>
                <el-button :disabled="!scope.row.isAlive" @click="handleClick(scope.row)" type="text" size="small">停止</el-button>
              </template>
            </el-table-column>
            <el-table-column
              label="配置操作"
              align="center"              
              width="120">
              <template slot-scope="scope">
                <el-button  @click="handleConfig(scope.row)" type="text" size="small">修改</el-button>
                <el-button  @click="handleCOnfig(scope.row)" type="text" size="small">删除</el-button>
              </template>
            </el-table-column>
          </el-table>    
      </el-main>
        <el-dialog width="551px" title="详细信息" :visible.sync="dialogTableVisible">
          <el-table :data="detail">
            <el-table-column align="center" prop="key" label="key" width="250"></el-table-column>
            <el-table-column align="center" prop="value" label="value" width="250"></el-table-column>
          </el-table>
        </el-dialog>
      

        <el-dialog title="Redis服务器配置"  :visible.sync="dialogFormVisible">
          <el-form :model="form">
            <el-form-item label="Redis名称" :label-width="formLabelWidth">
              <el-input v-model="form.name" width="150" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="RedisIP"  :label-width="formLabelWidth">
              <el-input v-model="form.name" width="150" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Redis端口"  :label-width="formLabelWidth">
              <el-input v-model="form.name" width="50" autocomplete="off"></el-input>
            </el-form-item>      
            <el-form-item label="服务器用户名" :label-width="formLabelWidth">
              <el-input v-model="form.name" width="150" autocomplete="off"></el-input>
            </el-form-item>   
             <el-form-item label="服务器密码" :label-width="formLabelWidth">
              <el-input v-model="form.name" width="200"  autocomplete="off"></el-input>
            </el-form-item>  
            <el-form-item label="Redis程序路径" :label-width="formLabelWidth">
              <el-input v-model="form.name" width="200"  autocomplete="off"></el-input>
            </el-form-item>  
            <el-form-item label="Redis配置路径" :label-width="formLabelWidth">
              <el-input v-model="form.name" width="200" autocomplete="off"></el-input>
            </el-form-item>  
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
          </div>
        </el-dialog>
    </el-container>
    
</template>

<script>
    
import qs from 'qs'
import $ from 'jquery'

export default {
    name: 'Redis',
    data(){
        return {
          items:[],
          dialogTableVisible: false,
          dialogFormVisible: false,
          detail:[],
          form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        },
        formLabelWidth: '120px'
        }
    },
    
    beforeMount() {
        var self = this;
        setInterval(myajax, 10000);
        function myajax() { 
            self.$http.get("/tqmanager/redis/health").then(function(res){
//            console.log(res.data.data)
              self.items = res.data.data.health;
            }).catch(function(err){
              console.log(err)
            })
            }  
        myajax();
    },
    
    methods:{
        handleClick(redis) {
            var data = {
                opertion : redis.isAlive? 0:1,
                id : redis.id
            };
            
            var params = new URLSearchParams();
            params.append("opertion", redis.isAlive? 0:1)
            params.append("id",redis.id);
            this.$http.post("/tqmanager/redis/task", params).then(function(res){
              console.log(res.data.rdesc)
            }).catch(function(err){
              console.log(err)
            })
            //TODO 点击后10秒内不能再点,还没有实现
            var click = event.target;
            var parent = $(click).parent();
            $(parent).addClass("is-disabled");
            $(parent).attr("disabled", "disabled");
        },
        
        detailInfo(row, event) {
            this.dialogTableVisible = true;
            this.detail = row.info;
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