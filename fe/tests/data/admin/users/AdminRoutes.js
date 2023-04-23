import AdminUsersOverview from "@/components/Admin/users/index.vue";
import AdminUserDelete from "@/components/Admin/users/delete.vue";
import AdminUserCreate from "@/components/Admin/users/create.vue";
import Homepage from "@/components/home/Homepage.vue";
import AdminUserUpdate from "@/components/Admin/users/update.vue";
import AdminUsersView from "@/components/Admin/users/show.vue";


export default class AdminRoutes {
    static adminRoutes() {
        return [
            {
                path: '/admin/users/', meta: {title: 'Admin | Users overview'}, component: AdminUsersOverview,
                children: [
                    {path: 'delete/:id', component: AdminUserDelete}
                ]
            },
            {path: '/admin/users', component: {default: AdminUsersOverview}},
            {path: '/admin/users/create', component: AdminUserCreate},
            {path: '/', component: AdminUsersOverview},
            {path: '/admin/users/update/:id', component: AdminUserUpdate},
            {path: '/admin/users/view/', component: AdminUsersView, query: {name: "id"}},
        ]
    }
}