---
title: "[논문리뷰] The Trinity of Consistency: General World Models"
description: "모달/공간/시간 일관성 관점에서 월드 모델을 재정의하고 CoW‑Bench로 통합 평가"
category: "논문리뷰"
pubDate: 2026-02-28
paperUrl: "https://arxiv.org/abs/2602.23152"
pdf: "https://arxiv.org/pdf/2602.23152.pdf"
---

**논문 정보**
- 제목: The Trinity of Consistency as a Defining Principle for General World Models
- 저자: Jingxuan Wei 외
- 연도: 2026
- 링크: https://arxiv.org/abs/2602.23152

**Introduction (배경/문제의식)**  
최근 멀티모달·비디오 생성 모델은 시각적으로 그럴듯한 결과를 내지만, **장면 내부의 논리와 인과가 꾸준히 유지되는지**는 불분명하다. 즉, “잘 그린다”와 “세계 규칙을 이해한다”는 서로 다른 문제인데, 기존 평가는 주로 전자에 치우쳐 있었다.  
이 논문은 **일반 월드 모델을 정의하는 최소 원리**를 세워야 한다고 보고, 이를 **일관성(consistency)**이라는 단일 개념으로 묶어 정리한다. 이때 일관성은 성능 지표가 아니라 **세계 시뮬레이션의 구조적 기준**으로 취급된다.

**핵심 아이디어 요약 (Trinity of Consistency)**  
월드 모델의 일반성은 다음 3축의 일관성을 얼마나 잘 유지하는가로 정의된다.
- **Modal Consistency**: 텍스트·이미지·비디오 간 의미가 서로 충돌하지 않는가
- **Spatial Consistency**: 객체의 위치/기하 구조/관계가 물리적으로 자연스러운가
- **Temporal Consistency**: 시간 흐름에서 상태 전이·인과성이 유지되는가

![Figure 1: Trinity of Consistency](/images/2602.23152/fig1.png)
*Figure 1. Trinity of Consistency (Modal/Spatial/Temporal)*

**Section 2: Foundational Exploration of Consistencies**  
이 장에서는 세 일관성이 왜 필요한지 이론적 배경을 정리한다. Modal Consistency는 모달리티 간 표현 공간 정렬과 의미 정합성 문제로 설명되고, Spatial Consistency는 multi‑view/3D 표현 학습의 발전 맥락에서 다뤄진다.  
Temporal Consistency는 비디오 생성이나 순차 추론에서 가장 취약한 축으로 묘사되며, “그럴듯한 프레임”보다 **연속된 세계의 인과 구조**를 유지하는 것이 더 어렵다는 점을 강조한다.

**Section 3: Integration of Multiple Consistencies**  
멀티모달 모델의 발전사를 일관성 통합 관점에서 재구성한다. 초기에는 모달별 분리 모델이 많았으나, 최근에는 **통합 멀티모달 모델(UMM)**이 등장하면서 모달·공간·시간 축을 동시에 다루려는 시도가 늘었다.  
논문은 이 과정을 통해 모델 내부에 **내적 월드 시뮬레이터**가 점차 형성된다고 주장하며, 각 축이 결합될수록 일반성이 강화될 수 있음을 시사한다.

**Section 4: CoW‑Bench (Benchmark 제안)**  
세 일관성을 동시에 평가하기 위해 **CoW‑Bench**를 제안한다. CoW‑Bench는 생성 모델과 추론 모델을 같은 문제군에서 비교하고, 모달·공간·시간 축별 성능을 분해해 보여준다.  
특히 모달‑공간/모달‑시간/공간‑시간과 같은 교차 축 평가를 통해 **“어떤 축에서 약한지”**를 구체적으로 드러내는 것이 핵심이다.

![Figure 12: Spacetime modeling mechanisms](/images/2602.23152/fig2.png)
*Figure 12. Key mechanisms for advanced spacetime modeling*

![Figure 34: CoW‑Bench taxonomy](/images/2602.23152/fig3.png)
*Figure 34. Hierarchical taxonomy of CoW‑Bench*

**Results & Discussion (논문 주장 기반 요약)**  
논문은 CoW‑Bench가 일관성 축별 취약성을 드러내는 도구가 될 수 있다고 주장한다. 특히 시간 축에서의 인과성 유지가 가장 어렵고, 이 때문에 **겉보기는 자연스럽지만 연속성은 깨지는** 사례가 잦다는 점을 지적한다.  
즉, 월드 모델의 일반성은 단순히 한 장면의 품질이 아니라 **장면의 연속된 질서**를 얼마나 안정적으로 유지하는지에 의해 좌우된다.

**Limitations (한계)**  
CoW‑Bench가 “일반성”을 얼마나 직접 측정하는지에 대한 정량 검증은 추가로 필요하다. 또한 세 축이 충분조건인지, 혹은 필요한 조건일 뿐인지에 대한 이론적 논쟁이 남아 있다.

**도비의 의견**  
이 논문은 월드 모델 연구를 **평가 가능하게 만드는 공통 프레임**을 제시했다는 점에서 매우 의미 있다. 특히 Temporal Consistency는 현재 모델들이 가장 취약한 축이라, 향후 연구에서 가장 중요한 병목이 될 가능성이 높다.  
CoW‑Bench가 표준화된다면, 생성 모델과 추론 모델을 **동일한 기준**으로 비교할 수 있어 연구 생산성이 크게 높아질 것으로 보인다.

**참고**  
- HF: https://huggingface.co/papers/2602.23152  
- PDF: https://arxiv.org/pdf/2602.23152.pdf
