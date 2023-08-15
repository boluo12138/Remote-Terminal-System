import request from "@/utils/request";

export default {
  getConvList(data) {
    return request({
      url: "/conv/list",
      method: "post",
      data,
    });
  },
  getConvAllList() {
    return request({
        url: '/conv/getList',
        method: 'get'
    });
},
  addConv(data) {
    return request({
      url: "/conv/add",
      method: "post",
      data,
    });
  },
  updateConv(data) {
    return request({
      url: "/conv/update",
      method: "post",
      data,
    });
  },
  deleteConvById(id) {
    return request({
        url: `/conv/delete/${id}`,
        method: 'delete'
    });
},
};
