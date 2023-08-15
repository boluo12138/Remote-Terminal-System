import request from "@/utils/request";

export default {
  getLogList(data) {
    return request({
      url: "/log/list",
      method: "post",
      data,
    });
  },
  addLog(data) {
    return request({
      url: "/log/add",
      method: "post",
      data,
    });
  },
  updateLog(data) {
    return request({
      url: "/log/update",
      method: "post",
      data,
    });
  },
  deleteLogById(id) {
    return request({
        url: `/log/delete/${id}`,
        method: 'delete'
    });
},
};
