from django.shortcuts import render
from django.http import HttpResponse
from ..models import Audio2Text, SpeechAnimation
from rest_framework import viewsets
from rest_framework import status
from rest_framework.response import Response
from rest_framework import permissions
from ..serializers import SpeechAnimation_Serializer
from ..serializers import Audio2Text_Serializer
from . import g2p_views

class SpeechAnimationView(viewsets.ModelViewSet):
    queryset = SpeechAnimation.objects.all()
    serializer_class = SpeechAnimation_Serializer
    permission_classes = (permissions.IsAuthenticated,)

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


class Audio2TextView(viewsets.ModelViewSet):
    queryset = Audio2Text.objects.all()
    serializer_class = Audio2Text_Serializer
    permission_classes = (permissions.IsAuthenticated,)

    def post(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        if request.method == 'POST':
            serializer.is_valid(raise_exception=True)
            self.perform_create(serializer)
            print("여기여기")
            graph = type(serializer.data.get('text'))
            graphemes = g2p_views.runKoG2P(graph, 'rulebook.txt')
            print(graphemes)
            
            # g2p로 연결 하고
            # g2p에 맞는 이미지를 사용하기
            serializer2 = self.get_serializer(data=graphemes)
            self.perform_create(serializer2)
            return Response(serializer.data, status=status.HTTP_201_CREATED)
            # is_text_include = type(serializer.data.get('text'))
            # text(serializer.data.get('text'))
            # render(request, "post_list.html", context=context)
        return Response(serializer.data, status=status.HTTP_400_BAD_REQUEST)

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
