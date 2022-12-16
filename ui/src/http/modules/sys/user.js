import axios from '../../axios'

/* 
 * 用户管理模块
 */

// 保存
export const save = (data) => {
    if (data.id == 0) {
        return axios({
            url: '/v1/sys/user/insert',
            method: 'post',
            data
        })
    } else {
        return axios({
            url: '/v1/sys/user/update',
            method: 'post',
            data
        })
    }
}

// 删除
export const deleteById = (id) => {
    return axios({
        url: '/v1/sys/user/delete/'+id,
        method: 'get'
    })
}

// 分页查询
export const findPage = (data) => {
    return axios({
        url: '/v1/sys/user/findPage',
        method: 'post',
        data
    })
}
// 导出Excel用户信息
export const exportUserExcelFile = (data) => {
    return axios({
        url: '/user/exportUserExcelFile',
        method: 'post',
        data
    })
}
// 查找用户的菜单权限标识集合
export const findPermissions = (params) => {
    return axios({
        url: '/user/findPermissions',
        method: 'get',
        params
    })
}
// 根据用户名查找
export const findByName = (params) => {
    return axios({
        url: '/user/findByName',
        method: 'get',
        params
    })
}
// 更新用户密码
export const updatePassword = (params) => {
    return axios({
        url: '/user/updatePassword',
        method: 'get',
        params
    })
}


// 获得当前用户的角色
export const findUserRoles = (user_id) => {
    return axios({
        url: '/v1/sys/user/findUserRoles/'+user_id,
        method: 'get'
    })
}

// 保存
export const saveRoles = (data) => {
    return axios({
        url: '/v1/sys/user/saveBatch',
        method: 'post',
        data
    })
}