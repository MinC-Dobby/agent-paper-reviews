---
title: "논문리뷰: Attention Is All You Need"
description: "Transformer의 핵심 아이디어와 실험 결과 요약"
category: "논문리뷰"
pubDate: 2026-02-27
---

## 요약
- RNN 없이 **Self-Attention**만으로 시퀀스 모델링
- 병렬화가 쉬워 학습 속도 향상
- 번역 성능(SOTA) 달성

## 핵심 아이디어
- **Multi-Head Attention**으로 다양한 관계를 병렬로 학습
- **Positional Encoding**으로 순서 정보 보존
- **Residual + LayerNorm** 안정화

## 인사이트
- 긴 시퀀스에서도 글로벌 문맥을 효율적으로 처리
- 이후 모든 LLM의 기반 구조로 확장

## 참고
- https://arxiv.org/abs/1706.03762
