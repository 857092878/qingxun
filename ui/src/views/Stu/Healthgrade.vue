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
            <el-upload :action="'http://localhost:8080/stu/Healthgrade/import'" :show-file-list="false" accept="xlsx"
                :on-success="handleExcelImportSuccess" style="display: inline-block">
                <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
            </el-upload>
            <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection"></el-table-column>
            <el-table-column prop="fhealthId" label="健康等级编号"></el-table-column>
            <el-table-column prop="fhealth" label="健康等级"></el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
                    <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
                        icon-color="red" title="您确定删除吗？" @confirm="del(scope.row.fhealthId)">
                        <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="params.pageNum"
                :page-sizes="[2, 5, 10, 20]" :page-size="params.pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>

        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="80px" size="small">
                <el-form-item label="健康等级">
                    <el-input v-model="form.fhealth" autocomplete="off"></el-input>
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
                }
            
    };
  },
  created() {
        this.load()
    },
    methods: {
        load() {
            this.$api.Healthgrade.findPage(this.params).then(res => {
                this.tableData = res.data.list
                this.total = res.data.total
                console.log(res)

            })
        },
        save() {
            console.log("带添加")
            this.$api.Healthgrade.add(this.form).then(res => {
                console.log("添加")
                if (res.code==200) {
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
            console.log("删除"+id)
            this.$api.Healthgrade.del(id).then(res => {
                if (res.code==200) {
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
            this.$api.Healthgrade.dels(ids).then(res => {
                if (res.code==200) {
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
            window.open("http://localhost:8080/stu/Healthgrade/export")
        }
    },

}
</script>
  
  
<style>
.headerBg {
    background: #eee !important;
}
</style>
