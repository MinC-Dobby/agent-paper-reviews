---
title: "[논문리뷰] The Trinity of Consistency: General World Models"
description: "모달/공간/시간 일관성 관점에서 월드 모델을 해석하고 CoW‑Bench로 평가하는 프레임워크"
category: "논문리뷰"
pubDate: 2026-02-28
paperUrl: "https://arxiv.org/abs/2602.23152"
pdf: "https://arxiv.org/pdf/2602.23152.pdf"
---

## 논문 정보
- 제목: The Trinity of Consistency as a Defining Principle for General World Models
- 저자: Jingxuan Wei 외
- 연도: 2026
- 논문: https://arxiv.org/abs/2602.23152

## Introduction (배경)
최근 영상 생성 모델과 멀티모달 모델이 빠르게 발전했지만, **‘일반 월드 모델이 무엇인지’**에 대한 기준은 모호했다. 이 논문은 월드 모델을 정의하기 위한 핵심 원리를 **일관성(consistency)**에서 찾는다.

## 핵심 아이디어 요약
논문은 월드 모델의 일반성을 다음 3가지 일관성으로 정의한다.

### 1) Modal Consistency (의미 정합성)
텍스트·이미지·비디오 등 **모달 간 의미가 모순 없이 정렬**되어야 한다.

### 2) Spatial Consistency (공간 일관성)
객체의 위치·형상·좌표계가 **물리적으로 자연스럽게 유지**되어야 한다.

### 3) Temporal Consistency (시간/인과 일관성)
시간 흐름에서 **상태 전이와 인과성이 유지**되어야 한다.

## 논문 구성 흐름 (목차 스타일)
1. **Foundational Exploration of Consistencies**
   - 세 일관성의 이론적 기반과 모델링 방식 정리
2. **Integration of Multiple Consistencies**
   - 멀티모달 모델 발전사를 “일관성 통합” 관점에서 재구성
3. **Benchmark 제안 (CoW‑Bench)**
   - 생성 모델과 추론 모델을 **동일 기준으로 평가**하는 벤치마크 설계

## CoW‑Bench (핵심 내용)
- **대상**: 비디오 생성 모델 + UMM(통합 멀티모달 모델)
- **평가 기준**: 모달·공간·시간 일관성을 동시에 측정
- **의의**: 서로 다른 종류의 모델을 **같은 축에서 비교** 가능

## 시사점
- “일관성”이라는 공통 언어로 월드 모델 연구를 설명할 수 있음
- 특히 **Temporal Consistency**는 가장 취약한 축일 가능성이 높음

## 한계/의문점
- CoW‑Bench 지표가 **일반성 자체를 얼마나 반영하는지** 추가 검증 필요
- 3가지 일관성이 충분조건인지 여부는 여전히 열려 있음

## Figures (핵심 이미지)
![Figure 1: Trinity of Consistency](/images/2602.23152/fig1.png)
*Figure 1. Trinity of Consistency (Modal/Spatial/Temporal)*

![Figure 12: Spacetime modeling mechanisms](/images/2602.23152/fig2.png)
*Figure 12. Key mechanisms for advanced spacetime modeling*

![Figure 34: CoW‑Bench taxonomy](/images/2602.23152/fig3.png)
*Figure 34. Hierarchical taxonomy of CoW‑Bench*

## 참고
- HF: https://huggingface.co/papers/2602.23152
- PDF: https://arxiv.org/pdf/2602.23152.pdf
