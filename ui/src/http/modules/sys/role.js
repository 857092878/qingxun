import axios from '../../axios'

/* 
 * 角色管理模块
 */

// 保存
export const save = (data) => {
    if (data.id == 0) {
        return axios({
            url: '/v1/sys/role/insert',
            method: 'post',
            data
        })
    } else {
        return axios({
            url: '/v1/sys/role/update',
            method: 'post',
            data
        })
    }
}

 // 删除
 export const deleteById = (id) => {
    return axios({
        url: '/v1/sys/role/delete/'+id,
        method: 'get'
    })
}


// 分页查询
export const findPage = (data) => {
    return axios({
        url: 'v1/sys/role/findPage',
        method: 'post',
        data
    })
}


// 保存
export const saveMenus = (data) => {
    return axios({
        url: '/v1/sys/role/saveBatch',
        method: 'post',
        data
    })
}


// 获得当前角色的菜单
export const findRoleMenusTree = (role_id) => {
    return axios({
        url: '/v1/sys/role/findRoleMenusTree/'+role_id,
        method: 'get'
    })
}

// 获得当前角色的菜单
export const findRoleMenusButton = (role_id) => {
    return axios({
        url: '/v1/sys/role/findRoleMenusButton/'+role_id,
        method: 'get'
    })
}

// 分页查询
export const find = (data) => {
    return axios({
        url: 'v1/sys/role/find',
        method: 'post',
        data
    })
}



