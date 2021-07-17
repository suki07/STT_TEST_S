import os
from PIL import Image

fix_img_path = r'C:\Users\오하은\PycharmProjects\all_code\annyeong' # 이미지들이 저장되어 있는 경로
img_list = os.listdir(fix_img_path)
img_list = [fix_img_path + '/' + x for x in img_list]
images = [Image.open(x) for x in img_list]

im = images[0]
im.save('gif_lip_annyeong.gif', save_all=True, append_images=images[1:], loop=0x01, duration=500)