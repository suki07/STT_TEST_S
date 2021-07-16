from rest_framework import serializers
from .models import SpeechAnimation, Audio2Text
from django.contrib.auth.models import User


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('id', 'username', 'email')


class SpeechAnimation_Serializer(serializers.ModelSerializer):
    user = UserSerializer(read_only=True)

    class Meta:
        model = SpeechAnimation
        fields = '__all__'
        read_only_fields = ('text', 'mouth_gif')


class Audio2Text_Serializer(serializers.ModelSerializer):
    # user = UserSerializer(read_only=True)

    class Meta:
        model = Audio2Text
        fields = ['text', 'created_at'] # '__all__'
        # fields = (
        #     # 'user',
        #     'text',
        #     # 'created_at',
        #           )
        read_only_fields = ('created_at',)