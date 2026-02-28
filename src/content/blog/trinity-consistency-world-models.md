---
title: "[논문리뷰] The Trinity of Consistency: General World Models"
description: "모달/공간/시간 일관성으로 월드 모델을 정의하고 CoW‑Bench로 평가하는 프레임워크"
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
최근 영상 생성·멀티모달 모델은 급격히 발전했지만, **“일반 월드 모델이 무엇인지”**에 대한 정의가 불분명하다. 논문은 모델이 실제 세계를 이해·시뮬레이션하려면 단순한 정합성 이상의 **원리적 기준**이 필요하다고 보고, 이를 ‘일관성(consistency)’으로 정리한다.

**핵심 아이디어 요약**  
월드 모델의 일반성을 설명하는 3가지 축:
- **Modal Consistency**: 텍스트/이미지/비디오 간 의미 정합성
- **Spatial Consistency**: 공간 구조·좌표계·객체 관계의 일관성
- **Temporal Consistency**: 시간 흐름에서 상태 전이·인과성의 연속성

**논문 전개(목차 흐름)**  
1) *Foundational Exploration of Consistencies*  
   - 세 축의 이론적 기반과 모델링 패러다임을 정리
2) *Integration of Multiple Consistencies*  
   - 멀티모달 모델 발전사를 “일관성 통합” 관점에서 재해석
3) *CoW‑Bench*  
   - 생성 모델과 추론 모델을 같은 기준으로 평가하는 벤치마크 제안

**CoW‑Bench 상세**  
- 대상: 비디오 생성 모델 + 통합 멀티모달 모델(UMM)
- 평가: 모달/공간/시간 일관성을 동시에 측정
- 의의: 서로 다른 모델들을 **동일 축**에서 비교 가능

**시사점**  
- 일관성 3축은 월드 모델 연구를 **하나의 공통 언어**로 묶는다.
- 특히 Temporal Consistency는 현재 모델의 병목이 될 가능성이 높다.

**한계/논쟁점**  
- CoW‑Bench 지표가 **일반성 자체를 얼마나 반영하는지** 추가 검증 필요
- 3가지 축이 충분조건인지에 대한 논쟁 여지

**Figures (핵심 이미지)**  
![Figure 1: Trinity of Consistency](/images/2602.23152/fig1.png)
*Figure 1. Trinity of Consistency (Modal/Spatial/Temporal)*

![Figure 12: Spacetime modeling mechanisms](/images/2602.23152/fig2.png)
*Figure 12. Key mechanisms for advanced spacetime modeling*

![Figure 34: CoW‑Bench taxonomy](/images/2602.23152/fig3.png)
*Figure 34. Hierarchical taxonomy of CoW‑Bench*

**내 생각**  
이 논문은 월드 모델 연구를 **평가 가능하게 만드는 프레임**을 제시했다는 점에서 가치가 크다. 특히 Temporal Consistency는 지금까지 가장 약했던 축으로 보이며, 향후 연구의 핵심 병목이 될 가능성이 높다.

**참고**  
- HF: https://huggingface.co/papers/2602.23152  
- PDF: https://arxiv.org/pdf/2602.23152.pdf
