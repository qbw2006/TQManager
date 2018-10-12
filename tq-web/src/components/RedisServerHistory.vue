<template>
    <el-dialog width="65%" :visible="show" @close="closeDialog()">
        <el-tabs value="first" type="card">
            <el-tab-pane label="监控信息" name="first">
                <el-row :gutter="20" type="flex" justify="space-around">
                    <el-col :span="5">
                        <div class="grid-content bg-purple">
                            <label class="redis-info-primary-label">&nbsp;USED MEM</label>
                            <span class="redis-info-primary-value">926,17K</span>
                        </div>
                    </el-col>
                    <el-col :span="5">
                        <div class="grid-content bg-purple">
                            <label class="redis-info-primary-label">&nbsp;MEM RATIO</label>
                            <span class="redis-info-primary-value">1.0</span>
                        </div>
                    </el-col>
                    <el-col :span="5">
                        <div class="grid-content bg-purple">
                            <label class="redis-info-primary-label">&nbsp;TOTAL KEYS</label>
                            <span class="redis-info-primary-value">10</span>
                        </div>
                    </el-col>
                    <el-col :span="5">
                        <div class="grid-content bg-purple">
                            <label class="redis-info-primary-label">&nbsp;&nbsp;&nbsp; CLIENTS</label>
                            <span class="redis-info-primary-value">10</span>
                        </div>
                    </el-col>
                    <el-col :span="5">
                        <div class="grid-content bg-purple">
                            <label class="redis-info-primary-label">&nbsp; COMMANDS</label>
                            <span class="redis-info-primary-value">1.0</span>
                        </div>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <div class="grid-content bg-purple">
                            <vue-highcharts id="container" :options.sync="option"></vue-highcharts>
                            <!--<div id="container"></div>-->
                        </div>
                    </el-col>
                </el-row>
            </el-tab-pane>
            <el-tab-pane label="数据库操作" name="second"></el-tab-pane>
        </el-tabs>
    </el-dialog>
</template>

<script>
    import qs from 'qs'
    import $ from 'jquery'
    import VueHighcharts from 'vue2-highcharts'
    import HighCharts from 'highcharts'
    export default {
        props: ['show', 'redisId'],
        components: {
            VueHighcharts
        },
        methods:{
            closeDialog() {
                this.show = false;
            }
        },
        mounted: function() {
            var self = this;
            $.ajax({
                url:"/tqmanager/redis/health/history/" + self.redisId,
                type:'POST', //GET
                async:false,    //或false,是否异步
                timeout:5000,    //超时时间
                dataType:'json',    //返回的数据格式：
                success:function(data,textStatus,jqXHR){
                    console.log(data.data.health)
                    var healthData = data.data.health;
                    for(var i in healthData) {
                        var itemCurrentMem = new Array();
                        itemCurrentMem.push(healthData[i].date);
                        itemCurrentMem.push(healthData[i].info.used_memory_rss);
                        self.currentMemData.push(itemCurrentMem);                  
    
                        var itemMaxMem = new Array();
                        itemMaxMem.push(healthData[i].date);
                        itemMaxMem.push(healthData[i].info.total_system_memory);
                        self.maxMemData.push(itemMaxMem);  
                    }                        
                },
                error:function(xhr,textStatus){
                },
                complete:function(){
                }
            });
        },

        data(){
            return {
                currentMemData:[],
                maxMemData:[],                
                option: {
                    title: {
                        text: '2010 ~ 2016 年太阳能行业就业人员发展情况',
                        align: 'left'
                    },
                    xAxis: {
                        type: 'datetime',
                        title: {
                            text: null
                        }
                    },
                    yAxis: {
                        
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'middle'
                    },
                    plotOptions: {
                        series: {
                            label: {
                                connectorAllowed: false
                            }
                        }
                    },
                    series: [{
                        name: 'Max',
                        data: this.maxMemData
                    }, {
                        name: 'Current',
                        data: this.currentMemData
                    }],
                    responsive: {
                        rules: [{
                            condition: {
                                maxWidth: 500
                            },
                            chartOptions: {
                                legend: {
                                    layout: 'horizontal',
                                    align: 'center',
                                    verticalAlign: 'bottom'
                                }
                            }
                        }]
                    }
                }  
            }
        }
    }
</script>

<style>
    .el-row {
        margin-bottom: 20px;
        &:last-child {
            margin-bottom: 0;
        }
    }
    .el-col {
        border-radius: 4px;
    }
    
    .bg-purple {
        background: #d3dce6;
    }
    
    .bg-purple-light {
        background: #e5e9f2;
    }
    
    .grid-content {
        border-radius: 4px;
        min-height: 36px;
    }
    
    .redis-info-primary-label {
        display: block;
        width: 90px;
        text-align: center;
        font-family: PingFang SC;
        color: #303133;
        margin: 0 auto;
        font-size: 10px;
    }
    
    .redis-info-primary-value {
        display: block;
        width: 150px;
        text-align: center;
        font-family: PingFang SC;
        color: #409EFF;
        margin: 0 auto;
        font-size: 40px;
    }
</style>