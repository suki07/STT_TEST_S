from django.db import models
from django.urls import reverse
from django.contrib.auth.models import User

# Create your models here.

# id - audio - text - image
class Audio2Text(models.Model):
    # user = models.ForeignKey(User, on_delete=models.CASCADE)
    # audio = models.FileField(upload_to='musics/', default='NAN')
    text = models.CharField(max_length=50, default='NAN')  # API의 output 글자 길이 확인 필요
    created_at = models.DateTimeField(auto_now_add=True)

    # 시간 지연 때문에 한국어도 여기서 받아와야하나?
    # mouth_gif = models.TextField()  # models.ImageField(blank=True)  # 구글 ㅋ를라우드 -> # url

    def __str__(self):
        return str(self.text)  # self.user.username + ',' + str(self.created_at) + str(self.text)

    # def get_absolute_url(self):
    #     return reverse('mouth', args=[str(self.id)])


# text - image
class SpeechAnimation(models.Model):
    text = models.CharField(max_length=30, default='NAN')
    graphme = models.CharField(max_length=30, default='NAN')
    mouth_gif = models.TextField()  # models.ImageField(blank=True)

    def __str__(self):
        return self.text

    # def get_absolute_url(self):
    #     return reverse('mouth', args=[str(self.id)])