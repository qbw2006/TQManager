<template>
    <el-dialog width="65%">
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
                            <vue-highcharts :options="option"></vue-highcharts>
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
    import highcharts from 'highcharts'
    export default {
        components: {
            VueHighcharts
        },
        
        data(){
            return {
                option: {
                    title: {
                        text: '2010 ~ 2016 年太阳能行业就业人员发展情况'
                    },
                    subtitle: {
                        text: '数据来源：thesolarfoundation.com'
                    },
                    yAxis: {
                        title: {
                            text: '就业人数'
                        }
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
                            },
                            pointStart: 2010
                        }
                    },
                    series: [{
                        name: '安装，实施人员',
                        data: [43934, 52503, 57177, 69658, 97031, 119931, 137133, 154175]
                    }, {
                        name: '工人',
                        data: [24916, 24064, 29742, 29851, 32490, 30282, 38121, 40434]
                    }, {
                        name: '销售',
                        data: [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
                    }, {
                        name: '项目开发',
                        data: [null, null, 7988, 12169, 15112, 22452, 34400, 34227]
                    }, {
                        name: '其他',
                        data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
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
    /*@import url("../css/base.css");*/
    /*@import url("../css/redis.css");*/
</style>
<template>
    <el-dialog width="65%">
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
                            <vue-highcharts :options="option"></vue-highcharts>
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
    import highcharts from 'highcharts'
    export default {
        components: {
            VueHighcharts
        },
        
        data(){
            return {
                option: {
                    title: {
                        text: '2010 ~ 2016 年太阳能行业就业人员发展情况'
                    },
                    subtitle: {
                        text: '数据来源：thesolarfoundation.com'
                    },
                    yAxis: {
                        title: {
                            text: '就业人数'
                        }
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
                            },
                            pointStart: 2010
                        }
                    },
                    series: [{
                        name: '安装，实施人员',
                        data: [43934, 52503, 57177, 69658, 97031, 119931, 137133, 154175]
                    }, {
                        name: '工人',
                        data: [24916, 24064, 29742, 29851, 32490, 30282, 38121, 40434]
                    }, {
                        name: '销售',
                        data: [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
                    }, {
                        name: '项目开发',
                        data: [null, null, 7988, 12169, 15112, 22452, 34400, 34227]
                    }, {
                        name: '其他',
                        data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
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
    /*@import url("../css/base.css");*/
    /*@import url("../css/redis.css");*/
</style>