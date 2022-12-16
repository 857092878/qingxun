import axios from '../../axios'

/* 
 * 机构管理模块
 */

// 保存
export const save = (data) => {
    if (data.id == 0) {
        return axios({
            url: '/v1/sys/dept/insert',
            method: 'post',
            data
        })
    } else {
        return axios({
            url: '/v1/sys/dept/update',
            method: 'post',
            data
        })
    }
}
    // 删除
    export const deleteById = (id) => {
        return axios({
            url: '/v1/sys/dept/delete/'+id,
            method: 'get'
        })
    }
    // 查询机构树
    export const findDeptTree = (deleted) => {
        return axios({
            url: 'v1/sys/dept/findTree' + "/" + deleted,
            method: 'get'
        })
    }