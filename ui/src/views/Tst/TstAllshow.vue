<template>
        <div class="page-container">
                <div class="data-list">
                        <!--工具栏-->
                        <div class="toolbar" style="float: left; padding-top: 10px; padding-left: 20px">
                                <el-form :inline="true" :model="filters" :size="size">
                                        <el-form-item>
                                                <kt-button icon="fa fa-refresh" :label="$t('action.refresh')"
                                                        perms="TstAllshow:view" type="primary" @click="handleQuery" />
                                        </el-form-item>
                                        <el-form-item>
                                                <kt-button icon="fa fa-plus" :label="$t('action.add')"
                                                        perms="TstAllshow:add" type="primary" @click="handleAdd" />
                                        </el-form-item>

                                </el-form>
                        </div>

                        <!--表格内容栏-->
                        <el-table :data="tableData" stripe size="mini" style="width: 98%" border
                                :row-style="{ height: 1 + 'px' }" :cell-style="{ padding: 3 + 'px' }"
                                :highlight-current-row="true" v-loading="loading"
                                :element-loading-text="$t('action.loading')">
                                <el-table-column prop="seq" align="center" label="序号" width="50px">
                                </el-table-column>
                                <el-table-column prop="fcode" align="center" label="民族编码"></el-table-column>
                                <el-table-column prop="fname" align="center" label="民族名称"></el-table-column>
                                <el-table-column prop="fsort" align="center" label="排序数值"></el-table-column>
                                <el-table-column prop="memo" align="center" label="备注"></el-table-column>
                                <el-table-column fixed="right" align="center" width="185"
                                        :label="$t('action.operation')">
                                        <template slot-scope="scope">
                                                <kt-button icon="fa fa-edit" :label="$t('action.edit')"
                                                        perms="TstAllshow:edit" @click="handleEdit(scope.row)" />
                                                <kt-button icon="fa fa-trash" :label="$t('action.delete')"
                                                        perms="TstAllshow:delete" type="danger"
                                                        @click="handleDelete(scope.row)" />
                                        </template>
                                </el-table-column>
                        </el-table>
                </div>
                <!-- 新增修改界面 -->
                <el-dialog :title="!dataForm.id ? '显示所有信息--新增' : '显示所有信息--修改'" width="50%" :visible.sync="dialogVisible"
                        :close-on-click-modal="false">
                        <el-form :model="dataForm" :rules="dataRules" ref="dataForm" @keyup.enter.native="submitForm()"
                                center label-width="80px" :size="size" style="text-align: left">
                                <el-form-item label="民族编码" prop="fcode">
                                        <el-input v-model="dataForm.fcode" placeholder="民族编码"></el-input>
                                </el-form-item>
                                <el-form-item label="民族名称" prop="fname">
                                        <el-input v-model="dataForm.fname" placeholder="民族名称"></el-input>
                                </el-form-item>
                                <el-form-item label="排序数值" prop="fsort">
                                        <el-input v-model="dataForm.fsort" placeholder="排序数值"></el-input>
                                </el-form-item>
                                <el-form-item label="备注" prop="memo">
                                        <el-input v-model="dataForm.memo" placeholder="备注"></el-input>
                                </el-form-item>
                        </el-form>
                        <span slot="footer" class="dialog-footer">
                                <el-button :size="size" @click="dialogVisible = false">{{ $t("action.cancel") }}
                                </el-button>
                                <el-button :size="size" type="primary" v-loading.fullscreen.lock="editLoading"
                                        element-loading-text="数据保存中，请稍候……" element-loading-spinner="el-icon-loading"
                                        element-loading-background="rgba(0, 0, 0, 0.8)" @click="submit()">{{
                                                        $t("action.comfirm")
                                        }}</el-button>
                        </span>
                </el-dialog>

        </div>
</template>
<script>
import KtButton from "@/views/Core/KtButton";
export default {
        components: {
                KtButton,
        },
        data() {
                return {
                        size: "small",
                        loading: false,
                        filters: { name: "", },
                        tableData: [],
                        dialogVisible: false,
                        dataForm: {
                                id: 0,
                                fcode: "",
                                fname: "",
                                fsort: 0,
                                memo: "",
                        },
                        dataRules: {
                                id: [{ required: true, message: "id不能为空！", trigger: "blur" },
                                ],
                                fcode: [{ required: true, message: "民族编码不能为空！", trigger: "blur" },
                                { min: 2, max: 2, message: "民族编码必须为2个字符长度！", trigger: "blur" },
                                ],
                                fname: [{ required: true, message: "民族名称不能为空！", trigger: "blur" },
                                { max: 20, message: "民族名称最大长度为20个字符长度！", trigger: "blur" },
                                ],
                                fsort: [{ required: true, message: "排序数值不能为空！", trigger: "blur" },
                                ],
                                memo: [{ max: 50, message: "备注最大长度为50个字符长度！", trigger: "blur" },
                                ],
                        },
                        editLoading: false,
                };
        },
        methods: {
                find: function () {
                        this.loading = true;
                        let queryConditions = {
                                listCondition: [],
                                listOrder: []
                        };
                        queryConditions.listCondition = [];
                        queryConditions.listOrder = [];
                        this.$api.TstAllshow.find(queryConditions)
                                .then((res) => {
                                        if (res.code == 200) {
                                                this.tableData = res.data;
                                        } else {
                                                this.$message({ message: "操作失败, " + res.msg, type: "error" });
                                                this.tableData = null;
                                        }
                                        this.loading = false;
                                })
                                .catch((err) => {
                                        this.$message({ message: "操作失败, " + err.message, type: "error" });
                                        this.tableData = null;
                                        this.loading = false;
                                });
                },

                handleQuery: function () {
                        this.find();
                },

                handleAdd: function () {
                        this.dialogVisible = true;
                        this.dataForm = {
                                id: 0,
                                fcode: "",
                                fname: "",
                                fsort: 0,
                                memo: "",
                        };
                },

                handleEdit: function (row) {
                        this.dialogVisible = true;
                        Object.assign(this.dataForm, row);
                },

                handleDelete(row) {
                        let params = Object.assign({}, row);
                        this.$confirm(
                                "确认删除:名称为'" + params.name + "'的选中记录吗？",
                                "提示",
                                { type: "warning", }
                        ).then(() => {
                                this.$api.TstAllshow.deleteById(params.id)
                                        .then((res) => {
                                                if (res.code == 200) {
                                                        this.find();
                                                        this.$message({ message: "删除成功", type: "success" });
                                                } else {
                                                        this.$message({ message: "操作失败, " + res.tip, type: "error", });
                                                }
                                        })
                                        .catch((err) => {
                                                this.$message({
                                                        message: "操作失败, " + err.message,
                                                        type: "error",
                                                });
                                        });
                        });
                },

                submit() {
                        this.$refs["dataForm"].validate((valid) => {
                                if (valid) {
                                        this.editLoading = true;
                                        let params = Object.assign({}, this.dataForm);
                                        this.$api.TstAllshow.save(params).then((res) => {
                                                if (res.code == 200) {
                                                        this.$message({ message: "操作成功", type: "success" });
                                                        this.dialogVisible = false;
                                                        this.$refs["dataForm"].resetFields();
                                                } else {
                                                        this.$message({ message: "操作失败, " + res.msg, type: "error", });
                                                }
                                                this.find();
                                                this.editLoading = false;
                                        })
                                                .catch((err) => {
                                                        this.$message({ message: "操作失败, " + err.message, type: "error", });
                                                        this.editLoading = false;
                                                });
                                }
                        });
                },
        },
        mounted() {
                this.find();
        },
};
</script>

<style scoped>
.data-list {
        background: white;
        border-radius: 10px;
        height: calc(100vh - 125px);
}

.el-table {
        margin: 0 auto;
}
</style>

