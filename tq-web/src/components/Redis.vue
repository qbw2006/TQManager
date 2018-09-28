<template>
    <el-container>
      <el-aside width="300px"></el-aside>
      <el-main>
           <el-button type="primary" @click="openForm('redisForm')">添加服务器</el-button>
           <el-table
            :data="items"
            stripe
            @row-dblclick="detailInfo"
            style="width: 1351px">
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
              sortable
              width="250">
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
              label="部署模式"
              sortable
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
                <el-button :disabled="scope.row.isAlive" @click="handleTask(scope.row)" type="text" size="small" >启动</el-button>
                <el-button :disabled="!scope.row.isAlive" @click="handleTask(scope.row)" type="text" size="small">停止</el-button>
              </template>
            </el-table-column>
            <el-table-column
              label=""
              align="center"              
              width="120">
              <template slot-scope="scope">
                <el-button @click="openShell(scope.row)" type="primary" icon="el-icon-view" size="mini" circle></el-button> 
                <el-button @click="delConfig(scope.row)" type="danger" icon="el-icon-delete" size="mini" circle></el-button>
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
          <el-form :model="form" :rules="rules" ref="redisForm" label-width="120px" >
            <el-form-item label="名称" prop="name">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="RedisIP" prop="redisHost">
              <el-input v-model="form.redisHost"></el-input>
            </el-form-item>
            <el-form-item label="Redis端口"  prop="redisPort">
              <el-input v-model.number="form.redisPort"></el-input>
            </el-form-item>      
            <el-form-item label="Redis密码" prop="redisPassword">
              <el-input v-model="form.redisPassword"></el-input>
            </el-form-item>        
            <el-form-item label="Redis部署模式" prop="redisMode">
              <el-input v-model="form.redisMode"></el-input>
            </el-form-item>              
            <el-form-item label="服务器用户名" prop="serverUsername">
              <el-input v-model="form.serverUsername"></el-input>
            </el-form-item>   
             <el-form-item label="服务器密码" prop="serverPassword">
              <el-input v-model="form.serverPassword"></el-input>
            </el-form-item>  
            <el-form-item label="Redis程序路径" prop="redisBinPath">
              <el-input v-model="form.redisBinPath"></el-input>
            </el-form-item>  
            <el-form-item label="Redis配置路径" prop="redisConfigPath">
              <el-input v-model="form.redisConfigPath"></el-input>
            </el-form-item>  
            <el-form-item>
                <el-button type="primary" @click="createConfig('redisForm')">创建</el-button>
                <el-button @click="dialogFormVisible = false">取消</el-button>
            </el-form-item>            
          </el-form>
        </el-dialog>
        
        <el-dialog  width="50%" :visible.sync="shellVisible" @close="closeShell()">
            <div id ="xterm-container"></div>
        </el-dialog>
    </el-container>
</template>

<script>
    
import qs from 'qs'
import $ from 'jquery'
import xm from 'xterm'

import { Terminal } from 'xterm';
import * as fit from 'xterm/lib/addons/fit/fit';
import * as attach from 'xterm/lib/addons/attach/attach';


export default {
    data(){
        return {
          items:[],
          dialogTableVisible: false,
          dialogFormVisible: false,
          shellVisible:false,
          webSocket:null,
          detail:[],
          
          
          form: {
          name: '',
          redisHost: '',
          redisPort: '',
          redisMode: '',
          redisPassword: '',
          serverUsername: '',
          serverPassword: '',
          redisBinPath: '',
          redisConfigPath: ''
        },
        rules: {
          name: [
            { required: true, message: '请输入redis名称', trigger: 'blur' },
            { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
          ],
          redisHost: [
           { required: true, message: '请输入redis地址', trigger: 'blur' }
          ],
          redisPort: [
              { required: true, message: '端口不能为空', trigger: 'blur' },
              { type: 'number', message: '端口必须为数字值', trigger: 'blur' }
          ],
          redisMode: [
           { required: true, message: '请输入redis部署模式', trigger: 'blur' }
          ],          
          redisPassword: [
            { required: false, message: '请输入密码', trigger: 'blur' }
          ],
          serverUsername: [
            { required: true, message: '请输入服务器用户名', trigger: 'blur' }
          ],
          serverPassword: [
            { required: true, message: '请输入服务器密码', trigger: 'blur' }
          ],
          redisBinPath: [
            { required: true, message: '请输入redis程序路径', trigger: 'blur' }
          ],
          redisConfigPath: [
            { required: true, message: '请输入redis配置路径', trigger: 'blur' }
          ]
        }
      }
    },
    
    beforeMount() {
        var self = this;
        setInterval(myajax, 10000);
        function myajax() { 
            self.$http.get("/tqmanager/redis/health").then(function(res){
              self.items = res.data.data.health;
            }).catch(function(err){
              console.log(err)
            })
            }  
        myajax();
    },
    
    methods:{
        handleTask(redis) {
            var data = {
                opertion : redis.isAlive? 0:1,
                id : redis.id
            };
            this.$http.post("/tqmanager/redis/task",qs.stringify(data)).then(function(res){
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
        
        closeShell(){
            this.term.dispose();
            this.webSocket.close();
        },
        openShell(redis) {
            var self = this;
            self.shellVisible = true;
            this.$nextTick(function (){
                
                let host = process.env.NODE_ENV === "development" ? "ws://localhost:8088" : "ws://10.1.6.37:8088";
                
                self.webSocket =  new WebSocket(host +"/websocket/" + redis.id); 
                self.term = new Terminal({ cursorBlink: true });
                self.shellId = redis.id;
                Terminal.applyAddon(fit);
                Terminal.applyAddon(attach);
                self.term.open(document.getElementById("xterm-container"));
                self.term.attach(self.webSocket);
                self.term.fit();     // This method is now available for usage                   
            })
       
        },
        
        detailInfo(row, event) {
            this.dialogTableVisible = true;
            this.detail = row.info;
        },
        openForm(formName) {
            if (this.$refs[formName]!==undefined) {
                this.$refs[formName].resetFields();
            }
            this.dialogFormVisible = true;
        },
        createConfig(formName) {
            
            this.$refs[formName].validate((valid) => {
                if (!valid) {
                    throw new Error('参数错误'); //验证判断
                }
            });
            
            this.$http.post("/tqmanager/redis", qs.stringify(this.form)).then(function(res){
              console.log(res.data.data.id)
            }).catch(function(err){
              console.log(err)
            })
            
            this.dialogFormVisible = false;
        },
        delConfig(redis) {
            var url = "/tqmanager/redis/"+redis.id;
            this.$http.delete(url).then(function(res){
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
    #xterm-container
    {
        position: relative !important;
    }
    /*@import url("../css/base.css");*/
    /*@import url("../css/redis.css");*/
</style>