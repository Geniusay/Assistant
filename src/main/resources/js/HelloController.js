import request from 'axios';export function hello() { return request({   url: '/hello/helloGet',   method: 'get',    });}export function helloNotRestful(id, name) { return request({   url: '/hello/helloNotRestful',   method: 'get',   param: {     id: id,     name: name,   } });}export function helloPost(body) { return request({   url: '/hello/helloPost',   method: 'post',   data: {     body: body,   } });}export function helloGetById(id) { return request({   url: '/hello/helloGet/'+id,   method: 'get',    });}