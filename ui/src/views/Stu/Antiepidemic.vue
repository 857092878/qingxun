<template>
    <div>
        <div style="margin: 15px 0px 0px 0px">
            <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search"
                v-model="params.search"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
                icon-color="red" title="您确定批量删除这些数据吗？" @confirm="delBatch">
                <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
            <el-upload :action="'http://localhost:8080/stu/Antiepidemic/import'" :show-file-list="false" accept="xlsx"
                :on-success="handleExcelImportSuccess" style="display: inline-block">
                <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
            </el-upload>
            <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection"></el-table-column>
            <el-table-column prop="id" label="id"></el-table-column>
            <el-table-column prop="fcode" label="学号"></el-table-column>
            <el-table-column prop="fname" label="学生名字"></el-table-column>
            <el-table-column prop="finCampus" label="是否在校"></el-table-column>
            <el-table-column prop="flocation" label="目前位置"></el-table-column>
            <el-table-column prop="fleaveTime" label="离校时间"></el-table-column>
            <el-table-column prop="fhealth" label="健康等级"></el-table-column>
            <el-table-column prop="fhealthMemo" label="健康描述"></el-table-column>
            <el-table-column prop="fstatusName" label="健康码"></el-table-column>
            <el-table-column prop="fvaccinCompleted" label="接种完全"></el-table-column>
            <el-table-column prop="fvaccinMemo" label="接种描述"></el-table-column>
            <el-table-column prop="fjourney" label="行程"></el-table-column>
            <el-table-column prop="memo" label="备注"></el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
                    <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
                        icon-color="red" title="您确定删除吗？" @confirm="del(scope.row.fstudentId)">
                        <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="params.pageNum" :page-sizes="[2, 5, 10, 20]" :page-size="params.pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>

        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="80px" size="small">
                <el-form-item label="学号">
                    <el-input v-model="form.fcode" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="学生名字">
                    <el-input v-model="form.fname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="是否在校">
                    <!-- <el-input v-model="form.finCampus" autocomplete="off"></el-input> -->
                    <el-select v-model="form.finCampus" placeholder="请选择班干部">
                        <el-option v-for="item in bed_fin" :key="item.k" :label="item.v" :value="item.k"
                            autocomplete="off">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="目前位置">
                    <el-input v-model="form.flocation" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="离校时间">
                    <el-date-picker v-model="form.fleaveTime" type="datetime" placeholder="选择日期时间" align="right"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="健康等级">
                    <el-select v-model="form.fhealthId" placeholder="请选择健康等级">
                        <el-option v-for="item in health" :key="item.fhealthId" :label="item.fhealth"
                            :value="item.fhealthId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="健康描述">
                    <el-input v-model="form.fhealthMemo" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="健康码">
                    <el-select v-model="form.fstatusId" placeholder="请选择健康等级">
                        <el-option v-for="item in healthStatus" :key="item.fstatusId" :label="item.fstatusName"
                            :value="item.fstatusId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="接种完全">
                    <el-input v-model="form.fvaccinCompleted" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="接种描述">
                    <el-input v-model="form.fvaccinMemo" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="行程">
                    <el-input v-model="form.fjourney" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="form.memo" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>
<script>
import KtTable from "@/views/Core/KtTable";
import KtButton from "@/views/Core/KtButton";
import ComplexFind from "@/components/ComplexFind";
export default {
    components: {
        KtTable,
        KtButton,
        ComplexFind,
    },
    data() {
        return {
            tableData: [],
            total: 0,
            email: "",
            address: "",
            form: {},
            dialogFormVisible: false,
            multipleSelection: [],
            headerBg: 'headerBg',
            params: {
                pageNum: 1,
                pageSize: 5,
                search: "",
            },
            bed_fin:[
                {k:0,v:"否"},
                {k:1,v:"是"}
            ],
            health: [],
            healthStatus: [],
            pickerOptions: {
                shortcuts: [{
                    text: '今天',
                    onClick(picker) {
                        picker.$emit('pick', new Date());
                    }
                }, {
                    text: '昨天',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24);
                        picker.$emit('pick', date);
                    }
                }, {
                    text: '一周前',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', date);
                    }
                }]
            },

        };
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.$api.Antiepidemic.findPage(this.params).then(res => {
                this.tableData = res.data.list
                this.total = res.data.total

            }),
                this.$api.Healthgrade.findAll().then(res => {
                    this.health = res.data
                }),
                this.$api.HealthStatus.findAll().then(res => {
                    this.healthStatus = res.data
                })
        },
        save() {
            console.log("带添加")
            this.$api.Antiepidemic.add(this.form).then(res => {
                console.log("添加")
                if (res.code == 200) {
                    this.$message.success("保存成功")
                    this.dialogFormVisible = false
                    this.load()
                } else {
                    this.$message.error("保存失败")
                }
            })
        },
        handleAdd() {
            this.dialogFormVisible = true
            this.form = {}
        },
        handleEdit(row) {
            console.log(row)
            this.form = row
            this.dialogFormVisible = true
        },
        del(id) {
            console.log(id)
            console.log("删除" + id)
            this.$api.Antiepidemic.del(id).then(res => {
                if (res.code == 200) {
                    this.$message.success("删除成功")
                    this.load()
                } else {
                    this.$message.error("删除失败")
                }
            })
        },
        handleSelectionChange(val) {
            console.log(val)
            this.multipleSelection = val
        },
        delBatch() {
            let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
            this.$api.Antiepidemic.dels(ids).then(res => {
                if (res.code == 200) {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
                }
            })
        },
        reset() {
            this.params.search = ""
            this.load()
        },
        handleSizeChange(pageSize) {
            console.log(pageSize)
            this.params.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {
            console.log(pageNum)
            this.params.pageNum = pageNum
            this.load()
        },
        handleExcelImportSuccess() {
            this.$message.success("导入成功")
            this.load()
        },
        exp() {
            window.open("http://localhost:8080/stu/Antiepidemic/export")
        }
    },

}
</script>
  
  
<style>
.headerBg {
    background: #eee !important;
}
</style>
