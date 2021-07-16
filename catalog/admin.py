from django.contrib import admin
from .models import Audio2Text, SpeechAnimation
# from django.core.files.storage import default_storage

# Register your models here.
admin.site.register(Audio2Text)
admin.site.register(SpeechAnimation)