import axios from '../../axios'

export const findPage = (data) => {
    return axios({
        url: '/stu/Student/findPage',
        method: 'post',
        data,
    })
}
export const add = (data) => {
    return axios({
        url: '/stu/Student/add',
        method: 'post',
        data,
    })
}
export const del = (data) => {
    return axios({
        url: '/stu/Student/del/'+data,
        method: 'post',
        // data,
    })
}
export const dels = (data) => {
    return axios({
        url: '/stu/Student/dels/'+data,
        method: 'post',
        // data,
    })
}