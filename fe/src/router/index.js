import {createRouter, createWebHashHistory} from "vue-router";

import Homepage from "@/components/home/Homepage";
import Leaderboards from "@/components/leaderboard/Leaderboards";
import Levels from "@/components/levels/Levels";
import Profile from "@/components/profiles/Profile.vue";
import UnknownRoute from "@/components/UnknownRoute";
import Journal from "@/components/journal/Journal.vue";
import Counting from "@/components/levels/counting/Counting";
import AdminDashboard from "@/components/Admin/AdminDashboard";
import journalMore from "@/components/journal/ViewNote.vue";
import createNew from "@/components/journal/CreateNew.vue";
import Login from "@/components/auth/Login";
import SignUp from "@/components/auth/SignUp";
import LostPassword from "@/components/auth/LostPassword";

import Quiz from "@/components/levels/quiz/Quiz";
import QuizHome from "@/components/levels/quiz/Home";
import QuizLobby from "@/components/levels/quiz/Lobby";
import QuizScoreboard from "@/components/levels/quiz/Scoreboard";

import AdminUsersOverview from '@/components/Admin/users'
import AdminUsersView from '@/components/Admin/users/show'
import AdminUserCreate from '@/components/Admin/users/create'
import AdminUserUpdate from '@/components/Admin/users/update'
import AdminUserDelete from '@/components/Admin/users/delete'

import AdminLevelsOverview from '@/components/Admin/levels'
import AdminViewLevel1 from '@/components/Admin/levels/level1/view'
import AdminViewLevel2 from '@/components/Admin/levels/level2/show'
import AdminUpdateLevel2 from '@/components/Admin/levels/level2/update'
import AdminCreateLevel2 from '@/components/Admin/levels/level2/create'
import AdminDeleteLevel2 from '@/components/Admin/levels/level2/delete'

import AdminQuestionsOverview from '@/components/Admin/questions/index'
import AdminCreateQuestion from '@/components/Admin/questions/create'
import AdminViewQuestion from '@/components/Admin/questions/show'
import AdminUpdateQuestion from '@/components/Admin/questions/update'
import AdminDeleteQuestion from '@/components/Admin/questions/delete'

import AdminPlayedLevelsOverview from '@/components/Admin/userLevels/index'
import AdminPlayedLevel1Overview from '@/components/Admin/userLevels/level1/show'
import AdminPlayedLevel2Overview from '@/components/Admin/userLevels/level2/show'
import AdminPlayedLevel1Delete from '@/components/Admin/userLevels/level1/delete.vue'
import AdminPlayedLevel2Delete from '@/components/Admin/userLevels/level2/delete.vue'

import Background from "@/components/profiles/settings/Background";
import Agenda from "@/components/events/Events";
import CountingOverview from "@/components/levels/counting/CountingOverview";
import SignOut from "@/components/auth/SignOut";
import EditNote from "@/components/journal/EditNote.vue";
import deleteCard from "@/components/journal/deleteCard.vue";

import CONFIG from "@/app-config";
import {AuthService} from "@/services/auth/authService";

const authService = new AuthService(CONFIG.BACKEND_URL + "/auth")

function requireAuth(to, from, next) {
    if (!authService.getId()) {
        next("sign-in")
    } else {
        next()
    }
}


function requireAuthAndAdmin(to, from, next) {
    if (!authService.getId() || !authService.isAdmin()) {
        next("sign-in")
    } else {
        next()
    }
}

export const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {path: '/', component: Homepage, meta: {title: 'Home'}},
        {path: '/sign-in', component: Login, meta: {title: 'Sign-in'}},
        {path: '/sign-up', component: SignUp, meta: {title: 'Sign-up'}},
        {path: '/sign-out', component: SignOut, meta: {title: 'Sign-out'}},
        {path: '/lost-password', component: LostPassword, meta: {title: 'Lost password'}},
        {path: '/leaderboards', component: Leaderboards, meta: {title: 'Leaderboards'}},
        {path: '/levels', component: Levels, meta: {title: 'Levels'}, beforeEnter: requireAuth},
        {
            path: '/levels/counting/overview',
            component: CountingOverview,
            meta: {title: 'Counting Overview', beforeEnter: requireAuth}
        },
        {path: '/levels/counting', component: Counting, meta: {title: 'Counting'}, beforeEnter: requireAuth},
        {path: '/levels/quiz', component: QuizHome, meta: {title: 'Quiz'}, beforeEnter: requireAuth},
        {
            path: '/levels/quiz/lobby/',
            component: QuizLobby,
            query: {name: "id"},
            meta: {title: 'Quiz Lobby'},
            beforeEnter: requireAuth
        },
        {
            path: '/levels/quiz/room',
            component: Quiz,
            query: {room: "roomId"},
            meta: {title: 'Quiz'},
            beforeEnter: requireAuth
        },
        {
            path: '/levels/quiz/scoreboard',
            query: {quiz: "quizId"},
            component: QuizScoreboard,
            meta: {title: "Quiz Scoreboard"},
            beforeEnter: requireAuth
        },
        {path: '/profile/manage/account', component: Profile, meta: {title: 'Profile'}, beforeEnter: requireAuth},
        {
            path: '/profile/manage/background', component: Background, meta: {title: 'Manage Background'},
            beforeEnter: requireAuth
        },
        {
            path: '/profile/journal',
            component: Journal,
            meta: {title: 'Journal'},
            beforeEnter: requireAuth,
            children: [
                {path: "delete/:id", component: deleteCard, beforeEnter: requireAuth}
            ]
        },
        {path: '/admin', component: AdminDashboard, meta: {title: 'Admin Dashboard'}, beforeEnter: requireAuthAndAdmin},
        {path: '/profile/journal/journalMore', component: journalMore, beforeEnter: requireAuth},
        {path: '/profile/journal/editNote', component: EditNote, beforeEnter: requireAuth},
        {path: '/profile/journal/createNew', component: createNew, beforeEnter: requireAuth},
        {
            path: '/admin/users',
            meta: {title: 'Admin | Users overview'},
            component: AdminUsersOverview,
            beforeEnter: requireAuthAndAdmin,
            children: [
                {path: 'delete/:id', component: AdminUserDelete, beforeEnter: requireAuthAndAdmin}
            ]
        },
        {
            path: '/admin/users/create',
            meta: {title: 'Admin | Create new user'},
            component: AdminUserCreate,
            beforeEnter: requireAuthAndAdmin
        },
        {
            path: '/admin/users/update/:id',
            meta: {title: 'Admin | Edit user'},
            component: AdminUserUpdate,
            beforeEnter: requireAuthAndAdmin
        },
        {
            path: '/admin/users/view/',
            component: AdminUsersView,
            beforeEnter: requireAuthAndAdmin,
            query: {name: "id"},
            meta: {title: "View User"}
        },
        {
            path: '/admin/levels',
            meta: {title: 'Admin | Levels overview'},
            component: AdminLevelsOverview,
            beforeEnter: requireAuthAndAdmin,
            children: [
                {path: 'level/2/delete/:id', component: AdminDeleteLevel2, beforeEnter: requireAuthAndAdmin}
            ]
        },
        {
            path: '/admin/questions',
            meta: {title: 'Admin | Questions overview'},
            component: AdminQuestionsOverview,
            beforeEnter: requireAuthAndAdmin,
            children: [
                {path: 'delete/:id', component: AdminDeleteQuestion, beforeEnter: requireAuthAndAdmin}
            ]
        },
        {
            path: '/admin/questions/create',
            meta: {title: 'Admin | Create new questions'},
            component: AdminCreateQuestion,
            beforeEnter: requireAuthAndAdmin
        },
        {
            path: '/admin/questions/view/',
            meta: {title: 'Admin | View question'},
            component: AdminViewQuestion,
            query: {name: "id"},
            beforeEnter: requireAuthAndAdmin
        },
        {
            path: '/admin/questions/update/:id',
            meta: {title: 'Admin | Update user'},
            component: AdminUpdateQuestion,
            beforeEnter: requireAuthAndAdmin
        },
        {
            path: '/admin/levels/level/1/view/:id',
            meta: {title: 'Admin | View level 1'},
            component: AdminViewLevel1,
            beforeEnter: requireAuthAndAdmin
        },
        {
            path: '/admin/levels/level/2/view/:id',
            meta: {title: 'Admin | View level 2'},
            component: AdminViewLevel2,
            beforeEnter: requireAuthAndAdmin
        },
        {
            path: '/admin/levels/level/2/update/:id',
            meta: {title: 'Admin | Update level 2'},
            component: AdminUpdateLevel2,
            beforeEnter: requireAuthAndAdmin
        },
        {
            path: '/admin/levels/level/2/create',
            meta: {title: 'Admin | Create new level 2'},
            component: AdminCreateLevel2,
            beforeEnter: requireAuthAndAdmin
        },
        {
            path: '/admin/played-levels',
            component: AdminPlayedLevelsOverview,
            beforeEnter: requireAuthAndAdmin,
            meta: {title: "Manage Played Levels"},
            children: [
                {path: 'level/1/delete/:id', component: AdminPlayedLevel1Delete, beforeEnter: requireAuthAndAdmin},
                {path: 'level/2/delete/:id', component: AdminPlayedLevel2Delete, beforeEnter: requireAuthAndAdmin}
            ]
        },
        {
            path: '/admin/played-levels/1/view',
            component: AdminPlayedLevel1Overview,
            query: {countingId: "countingId"},
            beforeEnter: requireAuthAndAdmin
        },
        {
            path: '/admin/played-levels/2/view',
            component: AdminPlayedLevel2Overview,
            query: {userId: "userId", quizId: "quizId"},
            beforeEnter: requireAuthAndAdmin
        },
        {path: '/events', component: Agenda, meta: {title: "Events"}, beforeEnter: requireAuth},
        {path: '/:pathMatch(.*)', component: UnknownRoute, meta: {title: "Page not found"}},
    ],
    // linkActiveClass: "bg-red-500 text-white"
})
router.beforeEach((to, from, next) => {
    // Using Object.keys to check if object has any property
    document.title = Object.keys(to.meta).length !== 0 ? to.meta.title : "No title"
    next()
});

