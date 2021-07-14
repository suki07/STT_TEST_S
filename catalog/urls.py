from django.urls import path, include
from .views import base_views, g2p_views
from .views.base_views import SpeechAnimationView, Audio2TextView
from rest_framework.urlpatterns import format_suffix_patterns

from .views import base_views, g2p_views

get_list = SpeechAnimationView.as_view({
    'post': 'create',
    'get': 'list',
})

get_detail = SpeechAnimationView.as_view({
    'get': 'retrieve',
    'put': 'update',
    'patch': 'partial_update',
    'delete': 'destroy',
})

post_list = Audio2TextView.as_view({
    'post': 'create',
    'get': 'list',
})

post_detail = Audio2TextView.as_view({
    'get': 'retrieve',
    'put': 'update',
    'patch': 'partial_update',
    'delete': 'destroy',
})

urlpatterns = format_suffix_patterns([
    path('auth/', include('rest_framework.urls', namespace='rest_framework')),
    path('gets/', get_list, name='get_list'),
    path('gets/<int:pk>', get_detail, name='get_detail'),
    path('posts/', post_list, name='post_list'),
    path('posts/<int:pk>', post_detail, name='post_detail'),
    path('', base_views.hello_world, name='hello_world'),
    path('mouth/', base_views.post_mouth, name='mouth'),
])