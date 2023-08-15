import request from "@/utils/request";

export default {
  getAllMenu() {
    return request({
      url: "/menu",
      method: "get",
    });
  },
  addMenu(data) {
    return request({
      url: "/menu/add",
      method: "post",
      data,
    });
  },
  updateMenu(data) {
    return request({
      url: "/menu/update",
      method: "post",
      data,
    });
  },
  deleteMenuById(data) {
    return request({
      url: "/menu/delete",
      method: "post",
      data,
    });
  },
};
