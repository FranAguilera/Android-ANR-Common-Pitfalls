# Android ANR Common Pitfalls
Basic Android App demoing main causes of ANR


| Basic layout  | ANR triggered | With last exit reason
|--------------|--------------|--------------
| ![image](https://user-images.githubusercontent.com/4230063/169922691-acf15b23-3338-45c8-b239-d3cc9b8ce452.png) | ![image](https://user-images.githubusercontent.com/4230063/169973446-fadb0b11-5c46-47c5-aecf-5277fb94b728.png) | ![image](https://user-images.githubusercontent.com/4230063/170195847-90aebe28-0eae-43ad-90f7-24cc25be29dd.png) |


| Deadlock demo - main held by tid=2  | Deadlock demo - background thread held by tid=1 
|--------------|-------------
|![image](https://user-images.githubusercontent.com/4230063/170209092-f3839b96-ddfd-4616-871c-ae5c0a41e7e6.png) | ![image](https://user-images.githubusercontent.com/4230063/170209397-15a60f89-623c-4b51-9cc8-93aae95ad8d5.png)



