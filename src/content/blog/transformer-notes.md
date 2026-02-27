---
title: "노트: Transformer 구조 한눈에 보기"
description: "Encoder/Decoder 흐름과 입력/출력 정리"
category: "노트"
pubDate: 2026-02-27
---

## 구조 요약
- **Encoder**: Self-Attention + FFN 반복
- **Decoder**: Masked Self-Attention → Cross-Attention → FFN

## 체크포인트
- Masked attention으로 미래 토큰 차단
- Cross-attention으로 인코더 정보 결합

## 다음에 볼 것
- LayerNorm 위치(Pre/Post)
- Attention Scaling 이유
