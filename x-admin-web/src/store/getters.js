const getters = {
  sidebar: (state) => state.app.sidebar,
  device: (state) => state.app.device,
  token: (state) => state.user.token,
  avatar: (state) => state.user.avatar,
  name: (state) => state.user.name,

  id: (state) => state.user.id,
  username: (state) => state.user.username,
  email: (state) => state.user.email,
  phone: (state) => state.user.phone,
  status: (state) => state.user.status,
  create_time: (state) => state.user.create_time,
  data: (state) => state.user.data,

  menuList: (state) => state.user.menuList,
  // path: state => state.user.menuList.path,
  roles: (state) => state.user.roles,

  visitedViews: (state) => state.tagsView.visitedViews,
  cachedViews: (state) => state.tagsView.cachedViews,
  permission_routes: (state) => state.permission.routes,
};
export default getters;
