# TODO: use win32 api to send key
import win32api
import win32con

VK_MEDIA_PLAY_PAUSE = 0xB3


def main():
    while True:
        try:

            a = input("enter your key code:")
            if a.upper().startswith('0X'):
                a = int(a, 16)
            else:
                a = int(a, 10)
            win32api.keybd_event(a, 0, 0, 0)
            win32api.keybd_event(a, 0, win32con.KEYEVENTF_KEYUP, 0)
            print(a)
        except Exception as e:
            print(e)
            break


if __name__ == '__main__':
    main()
