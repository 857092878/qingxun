import axios from '../../axios'

export const save = (data) => {
if (data.id == 0) {
return axios({
url: '/v1/student/StudentAllshow/insert',
method: 'post',
data
})
} else {
return axios({
url: '/v1/student/StudentAllshow/update',
method: 'post',
data
})
}
}

export const deleteById = (id) => {
return axios({
url: '/v1/student/StudentAllshow/delete/'+id,
method: 'get'
})
}

export const find = (data) => {
return axios({
url: 'v1/student/StudentAllshow/find',
method: 'post',
data
})
}

export const findChoice = () => {
return axios({
url: 'v1/student/StudentAllshow/findChoice',
method: 'get'
})
}

