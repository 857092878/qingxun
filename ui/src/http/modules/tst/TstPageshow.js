import axios from '../../axios'

export const save = (data) => {
if (data.id == 0) {
return axios({
url: '/v1/tst/TstPageshow/insert',
method: 'post',
data
})
} else {
return axios({
url: '/v1/tst/TstPageshow/update',
method: 'post',
data
})
}
}

export const deleteById = (id) => {
return axios({
url: '/v1/tst/TstPageshow/delete/'+id,
method: 'get'
})
}

export const findPage = (data) => {
return axios({
url: 'v1/tst/TstPageshow/findPage',
method: 'post',
data
})
}

//性别
export const findFgender = () => {
return axios({
url: '/v1/tst/TstPageshow/findFgender',
method: 'get'
})
}
