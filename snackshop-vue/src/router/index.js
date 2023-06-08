import { createRouter, createWebHashHistory } from "vue-router";
import store from "@/store";
import axiosUtil from '@/util/axios'


const routes = [

  {
    path: "/",
    name: "首页",
    component: () => import( "../views/layout/index.vue"),
    redirect:'/salesStatistic',
    children:[
      {
        path: "/home",
        name: "首页",
        component: () => import( "../views/home/index.vue"),
        redirect:'/salesStatistic',
        children:[
          {
            path:"/salesStatistic",
            name:"salesStatistic",
            component: () => import("../views/home/salesStatistic/salesStatistic.vue")
          },
          {
            path:"/orderStatistic",
            name:"orderStatistic",
            component: () => import("../views/home/orderStatistic/orderStatistic.vue")
          }

        ]
      },
      {
        path: "/userManage",
        name: "用户管理",
        component: () => import( "../views/userManage/index.vue")
      },
      {
        path: "/categoryManage",
        name: "分类管理",
        component: () => import( "../views/categoryManage/index.vue")
      },
      {
        path: "/goodsManage",
        name: "商品管理",
        component:() => import("../views/goodsManage/index.vue")
      },
      {
        path: "/orderManage",
        name: "订单管理",
        component:() => import("../views/orderManage/index.vue")
      },
      {
        path: "/modifyPassword",
        name: "修改密码",
        component:() => import("../views/modifyPassword/index.vue")
      }
    ]
  },
  {
    path: "/login",
    name: "login",
    component: () => import( "../views/login/index.vue")
  }

];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

/**
 * 路由导航
 * to：将要去哪个页面
 * from：从哪个页面过来
 * next:放行哪个页面
 */
router.beforeEach((to,from,next)=>{
  //判断用户是否登录
  const token = sessionStorage.getItem('token');
  if(!token){
    if(to.path === '/login'){
      next();
    }else {
      next(`/login?redirect=${to.fullPath}`);
    }
  }else {
    //判断vuex是否存在用户信息
    if(!store.state.phone ){
      //向后端发送请求 获取基本信息
      axiosUtil.get('/user/getInfo').then((res)=>{
        console.log(res);
        //得到用户信息
        const user = res.data.data;
        store.commit('setPhone',user.phone);
        store.commit('setAvatar',user.avatar);

      })
    }
    //已经登录
    if(to.path ==='login'){
      next('/home');
    }else {
      next();
    }

  }
})

export default router;
