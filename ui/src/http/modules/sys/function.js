import axios from '../../axios'

/* 
 * 功能管理模块
 */

// 保存
export const save = (data) => {
    if (data.id == 0) {
        return axios({
            url: '/v1/sys/function/insert',
            method: 'post',
            data
        })
    } else {
        return axios({
            url: '/v1/sys/function/update',
            method: 'post',
            data
        })
    }
}
    // 删除
    export const deleteById = (id) => {
        return axios({
            url: '/v1/sys/function/delete/'+id,
            method: 'get'
        })
    }
    // 查询页
    export const findPage = (data) => {
        return axios({
            url: 'v1/sys/function/findPage',
            method: 'post',
            data
        })
    }

    // 查询机构树
    export const findTree = () => {
        return axios({
            url: 'v1/sys/function/findTree',
            method: 'get'
        })
    }