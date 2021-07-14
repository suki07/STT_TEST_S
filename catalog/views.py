from django.shortcuts import render
from django.http import HttpResponse
from .models import Audio2Text, SpeechAnimation
from rest_framework import viewsets
from rest_framework import permissions
from .serializers import SpeechAnimation_Serializer
from .serializers import Audio2Text_Serializer


class SpeechAnimationView(viewsets.ModelViewSet):
    queryset = SpeechAnimation.objects.all()
    serializer_class = SpeechAnimation_Serializer
    permission_classes = (permissions.IsAuthenticated,)

    def perform_create(self, serializer):
        serializer.save(user=self.request.user)


class Audio2TextView(viewsets.ModelViewSet):
    queryset = Audio2Text.objects.all()
    serializer_class = Audio2Text_Serializer
    permission_classes = (permissions.IsAuthenticated,)

    def perform_create(self, serializer):
        serializer.save(user=self.request.user)


# Create your views here.
def hello_world(request):
   return HttpResponse(
                       "<h1>"
                       "Hello Byte!"
                       "</h1>"
   )

#
def post_mouth(request):

    mouth = SpeechAnimation.objects.filter(text='가').get()  # 이 '가'를 string 변수로? '안녕'
    context = {
        "mouth_gif": mouth.mouth_gif,
        "text": mouth.text,
    }
    return render(request, "post_list.html", context=context)
