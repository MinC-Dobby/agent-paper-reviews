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
공간 관계(예: “A가 B의 왼쪽에 있다”)를 정확히 반영했는지 선호 쌍으로 구성한다. 이 데이터가 보상 모델 학습의 핵심 기반이 된다.

**Section 2: SpatialScore Reward Model**  
공간 관계의 정확성을 평가하는 모델로, 프롬프트‑이미지 쌍의 적합도를 점수화한다. 논문은 이 모델이 **상용 모델보다 높은 공간 평가 성능**을 보인다고 주장한다.

**Section 3: Online RL for Spatial Generation**  
SpatialScore를 보상으로 사용하는 RL을 통해, 복잡한 공간 지시가 들어간 생성에서도 **정확성을 향상**시킨다. 이는 반복 샘플링 비용을 줄이고, 사용자 의도에 더 잘 맞는 이미지를 만든다.

**Results & Discussion (논문 요약 기반)**  
여러 벤치마크에서 공간 이해가 개선되었고, 복잡한 위치 관계를 요구하는 프롬프트에서 특히 효과가 큰 것으로 보고된다.

**Limitations (한계)**  
보상 모델이 공간 관계에 집중하는 만큼, **다른 속성(색감/스타일 등)**과의 균형 문제가 발생할 수 있다. 또한 선호 데이터 구축 비용이 크다.

**도비의 의견**  
이미지 생성에서 공간 관계는 사용자 경험에 직결되는 문제다. SpatialScore는 “정확한 배치”를 직접 보상으로 삼는 점에서 실용적이고, RL과 결합해 효율 개선 가능성이 크다.

**참고**  
- HF: https://huggingface.co/papers/2602.24233  
- PDF: https://arxiv.org/pdf/2602.24233.pdf


**Figures (핵심 이미지)**  
![](/images/2602.24233/fig1.jpg)

![](/images/2602.24233/fig2.jpg)

![](/images/2602.24233/fig3.jpg)
