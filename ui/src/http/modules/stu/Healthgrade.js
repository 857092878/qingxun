import axios from '../../axios'

export const findPage = (data) => {
    return axios({
        url: '/stu/Healthgrade/findPage',
        method: 'post',
        data,
    })
}
export const add = (data) => {
    return axios({
        url: '/stu/Healthgrade/add',
        method: 'post',
        data,
    })
}
export const del = (data) => {
    return axios({
        url: '/stu/Healthgrade/del/'+data,
        method: 'post',
        // data,
    })
}
export const dels = (data) => {
    return axios({
        url: '/stu/Healthgrade/dels/'+data,
        method: 'post',
        // data,
    })
}
export const findAll = () => {
    return axios({
        url: '/stu/Healthgrade/findAll/',
        method: 'post',
        // data,
    })
}
