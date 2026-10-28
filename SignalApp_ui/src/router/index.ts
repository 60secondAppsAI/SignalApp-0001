import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import Signals from '../components/Signals.vue';
import SignalDetail from '../components/SignalDetail.vue';
import SignalInputs from '../components/SignalInputs.vue';
import SignalInputDetail from '../components/SignalInputDetail.vue';
import SignalRules from '../components/SignalRules.vue';
import SignalRuleDetail from '../components/SignalRuleDetail.vue';
import SignalEvents from '../components/SignalEvents.vue';
import SignalEventDetail from '../components/SignalEventDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/signals',
    name: 'Signals',
    component: Signals
  },
  {
    path: '/signal/:id',
    name: 'SignalDetail',
    component: SignalDetail,
    props: true
  },
  {
    path: '/signalInputs',
    name: 'SignalInputs',
    component: SignalInputs
  },
  {
    path: '/signalInput/:id',
    name: 'SignalInputDetail',
    component: SignalInputDetail,
    props: true
  },
  {
    path: '/signalRules',
    name: 'SignalRules',
    component: SignalRules
  },
  {
    path: '/signalRule/:id',
    name: 'SignalRuleDetail',
    component: SignalRuleDetail,
    props: true
  },
  {
    path: '/signalEvents',
    name: 'SignalEvents',
    component: SignalEvents
  },
  {
    path: '/signalEvent/:id',
    name: 'SignalEventDetail',
    component: SignalEventDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
