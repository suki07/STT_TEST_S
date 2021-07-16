from django.shortcuts import render
from django.shortcuts import redirect
from django.http import HttpResponse
from ..models import Audio2Text, SpeechAnimation
from rest_framework import viewsets
from rest_framework import status
from rest_framework.response import Response
from rest_framework import permissions
from ..serializers import SpeechAnimation_Serializer
from ..serializers import Audio2Text_Serializer
from . import g2p_views

from django.views.decorators.csrf import csrf_exempt
from django.utils.decorators import method_decorator

from rest_framework.permissions import AllowAny
from rest_framework.response import Response
from rest_framework.views import APIView

class SpeechAnimationView(viewsets.ModelViewSet):
    queryset = SpeechAnimation.objects.all()
    serializer_class = SpeechAnimation_Serializer
    permission_classes = [AllowAny]

    # def get(self, request, *args, **kwargs):
    #     serializer = self.get_serializer(data=request.data)
    #     if request.method == 'POST':
    #         serializer.is_valid(raise_exception=True)
    #         self.perform_create(serializer)
    #         return Response(serializer.data, status=status.HTTP_201_CREATED)
    #         # is_text_include = type(serializer.data.get('text'))
    #         # text(serializer.data.get('text'))
    #     return Response(serializer.data, status=status.HTTP_400_BAD_REQUEST)

    def perform_create(self, serializer):
        serializer.save(user=self.request.user)


import logging
import os
# @method_decorator(csrf_exempt, name='dispatch')
class Audio2TextView(viewsets.ModelViewSet):
    queryset = Audio2Text.objects.all()
    serializer_class = Audio2Text_Serializer
    permission_classes = [AllowAny]

    print("모야")
    def post(self, request, *args, **kwargs):
        print("혹시")
        serializer = self.get_serializer(data=request.data)
        if request.method == 'POST':
            print("아??")
            if serializer.is_valid():  # fields = '__all__'
                serializer.save()

                context = self.analyze_graphmes(request.POST['text'])  # type(serializer.data.get('text'))
                Response(serializer.data, status=status.HTTP_201_CREATED)
                print("안녕")
                print(context['mouth_gif'])
                return redirect('detail', context)
                    # render(request, "post_list.html", context=context)
        return Response(serializer.data, status=status.HTTP_400_BAD_REQUEST)

    def analyze_graphmes(self, text):  # 음소 추출
        graphemes = g2p_views.runKoG2P(text, 'rulebook.txt')

        # 추후 진행상황에 따라 음소 별로 나누는 작업 필요: 현재는 시나리오에 완벽히 맞게 하고 있음.
        mouth = SpeechAnimation.objects.filter(text=text).get()
        context = {
            "mouth_gif": mouth.mouth_gif,
            "text": mouth.text
        }
        print(graphemes)
        return context


def detail(request, context):  #views.pk 변수명과 urls.pk 변수명이 같아야함.
    return render(request, 'post_list.html', context)

# print("여기여기")
            # graph = type(serializer.data.get('text'))
            # graphemes = g2p_views.runKoG2P(graph, 'rulebook.txt')
            # print(graphemes)
            #
            # g2p로 연결 하고
            # g2p에 맞는 이미지를 사용하기
            # serializer2 = self.get_serializer(data=graphemes)
            # self.perform_create(serializer2)# is_text_include = type(serializer.data.get('text'))
#             # text(serializer.data.get('text'))
#             # render(request, "post_list.html", context=context)


# Create your views here.
def hello_world(request):
   return HttpResponse(
                       "<h1>"
                       "Hello Byte!"
                       "</h1>"
   )

#
# def post_mouth(request):
#     # 특정 조건에 맞는 Row들을 가져오기 위해서 filter() 메서드 사용
#     # text 필드가 해당str 인 데이터
#     if request.method == 'POST':
#         text = request.POST['text']
#         mouth = SpeechAnimation.objects.filter(text=text).get()
#         context = {
#             "mouth_gif": mouth.mouth_gif,
#             "text": mouth.text
#         }
#         return render(request, "post_list.html", context=context)
#

def post_mouth(request):

    mouth = SpeechAnimation.objects.filter(text='가').get()  # 이 '가'를 string 변수로? '안녕'
    context = {
        "mouth_gif": mouth.mouth_gif,
        "text": mouth.text,
    }
    return render(request, "post_list.html", context=context)
