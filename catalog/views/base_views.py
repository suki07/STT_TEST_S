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


def detail(request, context):
    return render(request, 'post_list.html', context)


class SpeechAnimationView(viewsets.ModelViewSet):
    queryset = SpeechAnimation.objects.all()
    serializer_class = SpeechAnimation_Serializer
    permission_classes = [AllowAny]
    print("헤이힝[")
    def get(self, request, **kwargs):
        # 가장 최근에 추가된 스피치 텍스트 가져오기
        # speech_text = Audio2Text.objects.orderby('created_at').first()
        # text = speech_text.text  # graphme으로 바꾸기
        # print(text)
        # gif url 가져오기
        # context = self.analyze_graphmes(text)  # type(serializer.data.get('text'))
        serializer = SpeechAnimation_Serializer(self.queryset, many=True) # self.get_serializer(context, many=True)

        if request.method == 'GET':
            return Response(serializer.data, status=status.HTTP_202_ACCEPTED)  # redirect('detail', context)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def analyze_graphmes(self, text):  # 음소 추출
        graphemes = g2p_views.runKoG2P(text, 'rulebook.txt')
        # 추후 진행상황에 따라 음소 별로 나누는 작업 필요: 현재는 시나리오에 완벽히 맞게 하고 있음.
        mouth = self.queryset.filter(text=text)   # graphme 으로 바꾸기
        print(mouth)
        # context = {
        #     "mouth_gif": mouth.mouth_gif,
        #     "text": mouth.text
        # }
        print(graphemes)
        return mouth


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
                return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.data, status=status.HTTP_400_BAD_REQUEST)


# Create your views here.
def hello_world(request):
   return HttpResponse(
                       "<h1>"
                       "Hello Byte!"
                       "</h1>"
   )


def post_mouth(request):

    mouth = SpeechAnimation.objects.filter(text='가').get()  # 이 '가'를 string 변수로? '안녕'
    context = {
        "mouth_gif": mouth.mouth_gif,
        "text": mouth.text,
    }
    return render(request, "post_list.html", context=context)

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

