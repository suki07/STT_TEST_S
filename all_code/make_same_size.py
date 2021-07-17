import os
from PIL import Image

# 이미지 size 통일하기
img_path = r'C:\Users\오하은\PycharmProjects\all_code\consonant' # 이미지들이 저장되어 있는 경로
img_list = os.listdir(img_path) # img_path 디렉토리 내의 모든 파일을 리스트로 반환
img_list = [img_path + '/' + x for x in img_list]
images = [Image.open(x) for x in img_list]
for im, im_name in zip(images, img_list):
    img = im.resize((307, 238))
    img.save(im_name)