---
title: "[논문리뷰] Enhancing Spatial Understanding in Image Generation via Reward Modeling"
description: "공간 관계 이해를 강화하는 보상모델(SpatialScore)과 RL 기반 개선"
category: "논문리뷰"
pubDate: 2026-03-03
paperUrl: "https://arxiv.org/abs/2602.24233"
pdf: "https://arxiv.org/pdf/2602.24233.pdf"
---

**논문 정보**
- 제목: Enhancing Spatial Understanding in Image Generation via Reward Modeling
- 저자: Zhenyu Tang 외
- 연도: 2026
- 링크: https://arxiv.org/abs/2602.24233

**Introduction (배경/문제의식)**  
텍스트‑투‑이미지 모델은 화질과 창의성은 좋아졌지만, **복잡한 공간 관계**를 제대로 이해하지 못해 여러 번 샘플링해야 하는 문제가 있다. 논문은 공간 관계 자체를 평가하는 **보상 모델**을 도입해 이 문제를 해결한다.

**핵심 아이디어 요약**  
- **SpatialReward‑Dataset(8만+ 선호 쌍)** 구축
- 공간 관계를 평가하는 **SpatialScore** 보상 모델 제안
- 보상 모델 기반 **온라인 강화학습**으로 공간 이해 향상
- 여러 벤치마크에서 일관된 성능 개선 보고

**Section 1: SpatialReward Dataset**
![](/images/2602.24233/img-000.jpg)
*Figure 1*

![](/images/2602.24233/img-001.jpg)
*Figure 2*

![](/images/2602.24233/img-002.jpg)
*Figure 3*

![](/images/2602.24233/img-003.png)
*Figure 4*

![](/images/2602.24233/img-004.jpg)
*Figure 5*

![](/images/2602.24233/img-005.png)
*Figure 6*

![](/images/2602.24233/img-006.jpg)
*Figure 7*

![](/images/2602.24233/img-007.png)
*Figure 8*

![](/images/2602.24233/img-008.jpg)
*Figure 9*

![](/images/2602.24233/img-009.png)
*Figure 10*

![](/images/2602.24233/img-010.jpg)
*Figure 11*

![](/images/2602.24233/img-011.png)
*Figure 12*

![](/images/2602.24233/img-012.jpg)
*Figure 13*

![](/images/2602.24233/img-013.png)
*Figure 14*

![](/images/2602.24233/img-014.jpg)
*Figure 15*

![](/images/2602.24233/img-015.png)
*Figure 16*

![](/images/2602.24233/img-016.jpg)
*Figure 17*

![](/images/2602.24233/img-017.png)
*Figure 18*

![](/images/2602.24233/img-018.jpg)
*Figure 19*

![](/images/2602.24233/img-019.png)
*Figure 20*

![](/images/2602.24233/img-020.jpg)
*Figure 21*

![](/images/2602.24233/img-021.png)
*Figure 22*

![](/images/2602.24233/img-022.jpg)
*Figure 23*

![](/images/2602.24233/img-023.png)
*Figure 24*

![](/images/2602.24233/img-024.jpg)
*Figure 25*

![](/images/2602.24233/img-025.png)
*Figure 26*

![](/images/2602.24233/img-026.jpg)
*Figure 27*

![](/images/2602.24233/img-027.jpg)
*Figure 28*

![](/images/2602.24233/img-028.png)
*Figure 29*

![](/images/2602.24233/img-029.jpg)
*Figure 30*

![](/images/2602.24233/img-030.jpg)
*Figure 31*

![](/images/2602.24233/img-031.jpg)
*Figure 32*

![](/images/2602.24233/img-032.png)
*Figure 33*

![](/images/2602.24233/img-033.jpg)
*Figure 34*

![](/images/2602.24233/img-034.png)
*Figure 35*

![](/images/2602.24233/img-035.jpg)
*Figure 36*

![](/images/2602.24233/img-036.png)
*Figure 37*

![](/images/2602.24233/img-037.jpg)
*Figure 38*

![](/images/2602.24233/img-038.png)
*Figure 39*

![](/images/2602.24233/img-039.jpg)
*Figure 40*

![](/images/2602.24233/img-040.png)
*Figure 41*

![](/images/2602.24233/img-041.jpg)
*Figure 42*

![](/images/2602.24233/img-042.png)
*Figure 43*

![](/images/2602.24233/img-043.jpg)
*Figure 44*

![](/images/2602.24233/img-044.png)
*Figure 45*

![](/images/2602.24233/img-045.jpg)
*Figure 46*

![](/images/2602.24233/img-046.png)
*Figure 47*

![](/images/2602.24233/img-047.jpg)
*Figure 48*

![](/images/2602.24233/img-048.png)
*Figure 49*

![](/images/2602.24233/img-049.jpg)
*Figure 50*

![](/images/2602.24233/img-050.png)
*Figure 51*

![](/images/2602.24233/img-051.jpg)
*Figure 52*

![](/images/2602.24233/img-052.png)
*Figure 53*

![](/images/2602.24233/img-053.jpg)
*Figure 54*

![](/images/2602.24233/img-054.png)
*Figure 55*

![](/images/2602.24233/img-055.jpg)
*Figure 56*

![](/images/2602.24233/img-056.png)
*Figure 57*

![](/images/2602.24233/img-057.jpg)
*Figure 58*

![](/images/2602.24233/img-058.png)
*Figure 59*

![](/images/2602.24233/img-059.jpg)
*Figure 60*

![](/images/2602.24233/img-060.png)
*Figure 61*

![](/images/2602.24233/img-061.jpg)
*Figure 62*

![](/images/2602.24233/img-062.png)
*Figure 63*

![](/images/2602.24233/img-063.jpg)
*Figure 64*

![](/images/2602.24233/img-064.png)
*Figure 65*

![](/images/2602.24233/img-065.jpg)
*Figure 66*

![](/images/2602.24233/img-066.png)
*Figure 67*

![](/images/2602.24233/img-067.jpg)
*Figure 68*

![](/images/2602.24233/img-068.png)
*Figure 69*

![](/images/2602.24233/img-069.jpg)
*Figure 70*

![](/images/2602.24233/img-070.png)
*Figure 71*

![](/images/2602.24233/img-071.jpg)
*Figure 72*

![](/images/2602.24233/img-072.png)
*Figure 73*

Section 2: SpatialScore Reward Model**Section 3: Online RL for Spatial Generation**  
SpatialScore를 보상으로 사용하는 RL을 통해, 복잡한 공간 지시가 들어간 생성에서도 **정확성을 향상**시킨다. 이는 반복 샘플링 비용을 줄이고, 사용자 의도에 더 잘 맞는 이미지를 만든다.

**Results & Discussion (논문 요약 기반)**
![](/images/2602.24233/img-146.jpg)
*Figure 147*

![](/images/2602.24233/img-147.jpg)
*Figure 148*

![](/images/2602.24233/img-148.jpg)
*Figure 149*

![](/images/2602.24233/img-149.jpg)
*Figure 150*

![](/images/2602.24233/img-150.jpg)
*Figure 151*

![](/images/2602.24233/img-151.jpg)
*Figure 152*

![](/images/2602.24233/img-152.jpg)
*Figure 153*

![](/images/2602.24233/img-153.jpg)
*Figure 154*

![](/images/2602.24233/img-154.jpg)
*Figure 155*

![](/images/2602.24233/img-155.jpg)
*Figure 156*

![](/images/2602.24233/img-156.jpg)
*Figure 157*

![](/images/2602.24233/img-157.jpg)
*Figure 158*

![](/images/2602.24233/img-158.jpg)
*Figure 159*

![](/images/2602.24233/img-159.png)
*Figure 160*

![](/images/2602.24233/img-160.jpg)
*Figure 161*

![](/images/2602.24233/img-161.png)
*Figure 162*

![](/images/2602.24233/img-162.jpg)
*Figure 163*

![](/images/2602.24233/img-163.png)
*Figure 164*

![](/images/2602.24233/img-164.jpg)
*Figure 165*

![](/images/2602.24233/img-165.png)
*Figure 166*

![](/images/2602.24233/img-166.jpg)
*Figure 167*

![](/images/2602.24233/img-167.png)
*Figure 168*

![](/images/2602.24233/img-168.png)
*Figure 169*

![](/images/2602.24233/img-169.png)
*Figure 170*

![](/images/2602.24233/img-170.jpg)
*Figure 171*

![](/images/2602.24233/img-171.jpg)
*Figure 172*

![](/images/2602.24233/img-172.jpg)
*Figure 173*

![](/images/2602.24233/img-173.jpg)
*Figure 174*

![](/images/2602.24233/img-174.jpg)
*Figure 175*

![](/images/2602.24233/img-175.jpg)
*Figure 176*

![](/images/2602.24233/img-176.jpg)
*Figure 177*

![](/images/2602.24233/img-177.jpg)
*Figure 178*

![](/images/2602.24233/img-178.jpg)
*Figure 179*

![](/images/2602.24233/img-179.jpg)
*Figure 180*

![](/images/2602.24233/img-180.jpg)
*Figure 181*

![](/images/2602.24233/img-181.jpg)
*Figure 182*

![](/images/2602.24233/img-182.jpg)
*Figure 183*

![](/images/2602.24233/img-183.jpg)
*Figure 184*

![](/images/2602.24233/img-184.jpg)
*Figure 185*

![](/images/2602.24233/img-185.jpg)
*Figure 186*

![](/images/2602.24233/img-186.jpg)
*Figure 187*

![](/images/2602.24233/img-187.jpg)
*Figure 188*

![](/images/2602.24233/img-188.jpg)
*Figure 189*

![](/images/2602.24233/img-189.jpg)
*Figure 190*

![](/images/2602.24233/img-190.jpg)
*Figure 191*

![](/images/2602.24233/img-191.jpg)
*Figure 192*

![](/images/2602.24233/img-192.jpg)
*Figure 193*

![](/images/2602.24233/img-193.jpg)
*Figure 194*

![](/images/2602.24233/img-194.jpg)
*Figure 195*

![](/images/2602.24233/img-195.png)
*Figure 196*

![](/images/2602.24233/img-196.jpg)
*Figure 197*

![](/images/2602.24233/img-197.png)
*Figure 198*

![](/images/2602.24233/img-198.jpg)
*Figure 199*

![](/images/2602.24233/img-199.png)
*Figure 200*

![](/images/2602.24233/img-200.jpg)
*Figure 201*

![](/images/2602.24233/img-201.png)
*Figure 202*

![](/images/2602.24233/img-202.jpg)
*Figure 203*

![](/images/2602.24233/img-203.jpg)
*Figure 204*

![](/images/2602.24233/img-204.jpg)
*Figure 205*

![](/images/2602.24233/img-205.jpg)
*Figure 206*

![](/images/2602.24233/img-206.jpg)
*Figure 207*

![](/images/2602.24233/img-207.jpg)
*Figure 208*

![](/images/2602.24233/img-208.jpg)
*Figure 209*

![](/images/2602.24233/img-209.jpg)
*Figure 210*

![](/images/2602.24233/img-210.jpg)
*Figure 211*

![](/images/2602.24233/img-211.jpg)
*Figure 212*

![](/images/2602.24233/img-212.png)
*Figure 213*

![](/images/2602.24233/img-213.jpg)
*Figure 214*

![](/images/2602.24233/img-214.jpg)
*Figure 215*

![](/images/2602.24233/img-215.jpg)
*Figure 216*

![](/images/2602.24233/img-216.jpg)
*Figure 217*

![](/images/2602.24233/img-217.jpg)
*Figure 218*

![](/images/2602.24233/img-218.jpg)
*Figure 219*

Limitations (한계)**  
보상 모델이 공간 관계에 집중하는 만큼, **다른 속성(색감/스타일 등)**과의 균형 문제가 발생할 수 있다. 또한 선호 데이터 구축 비용이 크다.

**도비의 의견**  
이미지 생성에서 공간 관계는 사용자 경험에 직결되는 문제다. SpatialScore는 “정확한 배치”를 직접 보상으로 삼는 점에서 실용적이고, RL과 결합해 효율 개선 가능성이 크다.

**참고**  
- HF: https://huggingface.co/papers/2602.24233  
- PDF: https://arxiv.org/pdf/2602.24233.pdf
