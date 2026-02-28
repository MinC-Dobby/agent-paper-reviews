---
title: "논문리뷰: The Trinity of Consistency as a Defining Principle for General World Models"
description: "모달·공간·시간 일관성으로 일반 월드 모델을 정의하고 CoW‑Bench로 통합 평가"
category: "논문리뷰"
pubDate: 2026-02-28
paperUrl: "https://huggingface.co/papers/2602.23152"
pdf: "https://arxiv.org/pdf/2602.23152.pdf"
---

## 들어가며
이 논문은 **일반 월드 모델(General World Model)**이 갖춰야 할 최소 조건을 “일관성(consistency)”이라는 렌즈로 재정의한다. 단순히 텍스트‑이미지‑비디오를 잘 맞추는 수준을 넘어, **모달·공간·시간 축에서 모두 일관된 세계 시뮬레이션**이 가능해야 한다는 주장이다.

## 문제의식
- 영상 생성과 멀티모달 모델은 빠르게 발전했지만, **“일반성”을 판별할 기준**이 불명확하다.
- 모델이 **물리 법칙과 인과성을 내재화**했는지 평가하기 위해서는 공통된 기준이 필요하다.

## 핵심 주장: Trinity of Consistency
논문은 월드 모델이 반드시 만족해야 할 3가지 일관성을 제시한다.

1) **Modal Consistency (의미 정합성)**
- 텍스트‑이미지‑비디오 간 의미가 모순 없이 정렬되어야 한다.
- 서로 다른 모달리티가 **같은 사건/객체**를 동일하게 지시해야 한다.

2) **Spatial Consistency (기하 일관성)**
- 객체의 위치, 크기, 좌표계, 시점 변화가 물리적으로 자연스럽게 유지되어야 한다.
- 멀티뷰·3D·NeRF/가우시안 등 공간 표현의 발전이 이 축을 강화해왔다.

3) **Temporal Consistency (시간/인과 일관성)**
- 프레임 간 상태 전이가 물리 법칙을 위반하지 않아야 한다.
- 객체의 정체성, 운동, 상호작용이 시간적으로 끊기지 않아야 한다.

## 논문 전개(구성 요약)
- **Foundational Exploration of Consistencies**: 세 일관성의 이론적 기반과 모델링 패러다임 정리
- **Integration of Multiple Consistencies**: 멀티모달 학습의 발전사를 “일관성 통합” 관점으로 재해석
- **Benchmark 제안**: CoW‑Bench로 생성/추론 모델을 통합 비교

## CoW‑Bench 요약
- **대상**: 비디오 생성 모델 + 통합 멀티모달 모델(UMM)
- **목표**: 모달/공간/시간 일관성을 동시에 평가
- **의의**: 생성 모델과 추론 모델을 같은 지표로 비교 가능

## 시사점
- 일관성 3요소는 월드 모델 연구를 **하나의 공통 언어**로 묶는다.
- 향후 연구는 “어느 축이 병목인지”를 정량적으로 드러낼 수 있다.

## 한계와 논쟁점
- CoW‑Bench의 **정량 지표 설계**가 일반성에 얼마나 직결되는지는 추가 검증이 필요하다.
- ‘일관성’이 충분조건인지, 혹은 필요한 조건 중 일부인지 논쟁 여지가 있다.

## 내 생각
- 이 논문은 월드 모델을 **평가 가능하게 만드는 프레임**을 제시했다는 점에서 가치가 크다.
- 특히 Temporal Consistency는 현재 모델들이 가장 약한 축이므로, 향후 연구의 핵심 병목이 될 가능성이 높다.

## Figures (논문 이미지)
![Figure 1: The Trinity of Consistency in world models: Modal Consistency (Semantics), Spatial Consistency](/images/2602.23152/img-000.png)
*Figure 1: The Trinity of Consistency in world models: Modal Consistency (Semantics), Spatial Consistency*

![Figure 2: Performance Comparison of Mainstream Models across Different Tasks. The score has been](/images/2602.23152/img-001.png)
*Figure 2: Performance Comparison of Mainstream Models across Different Tasks. The score has been*

![Figure 3: Unified Representation Goal. Modal consistency aims to project heterogeneous inputs (Text,](/images/2602.23152/img-002.png)
*Figure 3: Unified Representation Goal. Modal consistency aims to project heterogeneous inputs (Text,*

![Figure 4: Evolution of Modal Consistency: From Geometric Isolation to Cognitive Alignment](/images/2602.23152/img-003.png)
*Figure 4: Evolution of Modal Consistency: From Geometric Isolation to Cognitive Alignment*

![Figure 5: The Modal Gap Challenge. (Left) Ideal hypersphere alignment assumes uniform distribution.](/images/2602.23152/img-004.png)
*Figure 5: The Modal Gap Challenge. (Left) Ideal hypersphere alignment assumes uniform distribution.*

![Figure 6: Evolution of Multimodal Fusion Paradigms. Transitioning from geometric isolation (Dual-](/images/2602.23152/img-005.png)
*Figure 6: Evolution of Multimodal Fusion Paradigms. Transitioning from geometric isolation (Dual-*

![Figure 7: Information Asymmetry in LLaVA. The linear projection layer Wproj acts as a low-rank](/images/2602.23152/img-006.jpg)
*Figure 7: Information Asymmetry in LLaVA. The linear projection layer Wproj acts as a low-rank*

![Figure 8: MM-DiT Architecture. By maintaining independent weight sets for both text and image](/images/2602.23152/img-007.jpg)
*Figure 8: MM-DiT Architecture. By maintaining independent weight sets for both text and image*

![Figure 9: Spatial Consistency via Multi-View Constraints. The model ensures that the generated subject](/images/2602.23152/img-008.jpg)
*Figure 9: Spatial Consistency via Multi-View Constraints. The model ensures that the generated subject*

![Figure 10: Evolution of Spatial Consistency Paradigms: From 2D Proxy to Generative Primitives.](/images/2602.23152/img-009.jpg)
*Figure 10: Evolution of Spatial Consistency Paradigms: From 2D Proxy to Generative Primitives.*

![Figure 11: Evolution of Spatial Consistency Paradigms. We trace the trajectory from early 2D Proxy](/images/2602.23152/img-010.jpg)
*Figure 11: Evolution of Spatial Consistency Paradigms. We trace the trajectory from early 2D Proxy*

![Figure 12CR1(c)). This paradigm discretizes the scene into a set of anisotropic Gaussian primitives](/images/2602.23152/img-011.jpg)
*Figure 12CR1(c)). This paradigm discretizes the scene into a set of anisotropic Gaussian primitives*

![Figure 12: Key Mechanisms for Advanced Spacetime Modeling. A taxonomy of core techniques](/images/2602.23152/img-012.jpg)
*Figure 12: Key Mechanisms for Advanced Spacetime Modeling. A taxonomy of core techniques*

![Figure 13: Evolution of Temporal Consistency Paradigms: From Latent Inflation and Discrete Sequence](/images/2602.23152/img-013.jpg)
*Figure 13: Evolution of Temporal Consistency Paradigms: From Latent Inflation and Discrete Sequence*

![Figure 14: Temporal Consistency and Identity Preservation. An illustration of the temporal attention](/images/2602.23152/img-014.jpg)
*Figure 14: Temporal Consistency and Identity Preservation. An illustration of the temporal attention*

![Figure 15: Video Consistency Analysis: Spatial vs. Frequency View. Traditional distribution-based](/images/2602.23152/img-015.jpg)
*Figure 15: Video Consistency Analysis: Spatial vs. Frequency View. Traditional distribution-based*

![Figure 16: Evolution of Video Generation Paradigms. The technical path advances from Temporal](/images/2602.23152/img-016.jpg)
*Figure 16: Evolution of Video Generation Paradigms. The technical path advances from Temporal*

![Figure 17: Tool-use & Closed-loop Verification. Diagram illustrating the ReAct paradigm, where an AI](/images/2602.23152/img-017.png)
*Figure 17: Tool-use & Closed-loop Verification. Diagram illustrating the ReAct paradigm, where an AI*

![Figure 18: Modal + Spatial Consistency: Language Controls Precise Spatial Relations. The model](/images/2602.23152/img-018.jpg)
*Figure 18: Modal + Spatial Consistency: Language Controls Precise Spatial Relations. The model*

![Figure 19: Evolution of Modal Consistency and Spatial Consistency.](/images/2602.23152/img-019.png)
*Figure 19: Evolution of Modal Consistency and Spatial Consistency.*

![Figure 20: Instruction-Driven Image Editing. The diagram illustrating structure-preserving image](/images/2602.23152/img-020.jpg)
*Figure 20: Instruction-Driven Image Editing. The diagram illustrating structure-preserving image*

![Figure 21: Pose-Aligned View Synthesis. Diagram illustrating pose-conditioned generation using a](/images/2602.23152/img-021.jpg)
*Figure 21: Pose-Aligned View Synthesis. Diagram illustrating pose-conditioned generation using a*

![Figure 22: Multimodal Alignment. The diagram illustrating the convergence of Text, Image, and](/images/2602.23152/img-022.jpg)
*Figure 22: Multimodal Alignment. The diagram illustrating the convergence of Text, Image, and*

![Figure 23: Modal + Temporal Consistency: Language Controls Time Evolution. The model integrates](/images/2602.23152/img-023.jpg)
*Figure 23: Modal + Temporal Consistency: Language Controls Time Evolution. The model integrates*

![Figure 24: Evolution of Modal Consistency and Temporal Consistency.](/images/2602.23152/img-024.jpg)
*Figure 24: Evolution of Modal Consistency and Temporal Consistency.*

![Figure 25: Autoregressive-Diffusion Model. It depicts an AR Planner constructing a causal temporal](/images/2602.23152/img-025.jpg)
*Figure 25: Autoregressive-Diffusion Model. It depicts an AR Planner constructing a causal temporal*

![Figure 26: Multi-Condition Decoupling Architecture. The diagram illustrating the Two-Stream Archi-](/images/2602.23152/img-026.jpg)
*Figure 26: Multi-Condition Decoupling Architecture. The diagram illustrating the Two-Stream Archi-*

![Figure 27: Unified Comprehension and Generation Symbiosis Architecture. It features a central LLM](/images/2602.23152/img-027.png)
*Figure 27: Unified Comprehension and Generation Symbiosis Architecture. It features a central LLM*

![Figure 28: Temporal + Spatial Consistency: Dynamic Object Permanence across Occlusion. The model](/images/2602.23152/img-028.jpg)
*Figure 28: Temporal + Spatial Consistency: Dynamic Object Permanence across Occlusion. The model*

![Figure 29: Evolution of Spatial Consistency and Temporal Consistency.](/images/2602.23152/img-029.png)
*Figure 29: Evolution of Spatial Consistency and Temporal Consistency.*

![Figure 30: Geometric Embedding Injection. It features a Doge on a perspective grid with a trajectory,](/images/2602.23152/img-030.jpg)
*Figure 30: Geometric Embedding Injection. It features a Doge on a perspective grid with a trajectory,*

![Figure 31: Trajectory-Centric Foundation Models. This illustrates the transformation of a 2D video](/images/2602.23152/img-031.jpg)
*Figure 31: Trajectory-Centric Foundation Models. This illustrates the transformation of a 2D video*

![Figure 32: The Trinity of Consistency. It depicts a Robot Doge performing embodied tasks (stacking](/images/2602.23152/img-032.jpg)
*Figure 32: The Trinity of Consistency. It depicts a Robot Doge performing embodied tasks (stacking*

![Figure 33: Evaluation of benchmarks.](/images/2602.23152/img-033.png)
*Figure 33: Evaluation of benchmarks.*

![Figure 34: Hierarchical Taxonomy of CoW-Bench. The inner ring represents the main consistency](/images/2602.23152/img-034.jpg)
*Figure 34: Hierarchical Taxonomy of CoW-Bench. The inner ring represents the main consistency*

![Figure 35 visualizes Modal–Space consistency, where models must map language constraints (entities,](/images/2602.23152/img-035.png)
*Figure 35 visualizes Modal–Space consistency, where models must map language constraints (entities,*

![Figure 35: Modal–Space consistency on CoW-Bench, shown as a heatmap over sub-metrics (rows)](/images/2602.23152/img-036.jpg)
*Figure 35: Modal–Space consistency on CoW-Bench, shown as a heatmap over sub-metrics (rows)*

![Figure 36 visualizes Modal–Time consistency, where models must (i) keep language-specified anchors](/images/2602.23152/img-037.jpg)
*Figure 36 visualizes Modal–Time consistency, where models must (i) keep language-specified anchors*

![Figure 36: Modal–Time consistency on CoW-Bench, shown as a heatmap over sub-metrics (rows) and](/images/2602.23152/img-038.png)
*Figure 36: Modal–Time consistency on CoW-Bench, shown as a heatmap over sub-metrics (rows) and*

![Figure 37: Time-Space consistency on CoW-Bench, shown as a heatmap over sub-metrics (rows) and](/images/2602.23152/img-039.png)
*Figure 37: Time-Space consistency on CoW-Bench, shown as a heatmap over sub-metrics (rows) and*

![Figure 38: Example diagrams of Modal sub-tasks.](/images/2602.23152/img-040.png)
*Figure 38: Example diagrams of Modal sub-tasks.*

![Figure 41, in Sem-Planar, models successfully identify a specific vehicle with a ”blue roof” within](/images/2602.23152/img-041.png)
*Figure 41, in Sem-Planar, models successfully identify a specific vehicle with a ”blue roof” within*

![Figure 50: Evolutionary spectrum of World Model paradigms based on interactive action spaces. This](/images/2602.23152/img-042.png)
*Figure 50: Evolutionary spectrum of World Model paradigms based on interactive action spaces. This*

![Figure 39: Example diagrams of Spatial sub-tasks.](/images/2602.23152/img-043.png)
*Figure 39: Example diagrams of Spatial sub-tasks.*

![Figure 40: Example diagrams of Temporal sub-tasks.](/images/2602.23152/img-044.png)
*Figure 40: Example diagrams of Temporal sub-tasks.*

![Figure 41: Example diagrams of modal and spatial sub-tasks.](/images/2602.23152/img-045.png)
*Figure 41: Example diagrams of modal and spatial sub-tasks.*

![Figure 42: Example diagrams of modal and temporal sub-tasks.](/images/2602.23152/img-046.jpg)
*Figure 42: Example diagrams of modal and temporal sub-tasks.*

![Figure 43: Example diagrams of spatial and temporal sub-tasks.](/images/2602.23152/img-047.jpg)
*Figure 43: Example diagrams of spatial and temporal sub-tasks.*

![Figure 44: Comparison with different models for modal consistency task.](/images/2602.23152/img-048.png)
*Figure 44: Comparison with different models for modal consistency task.*

![Figure 45: Comparison with different models for spatial consistency task.](/images/2602.23152/img-049.png)
*Figure 45: Comparison with different models for spatial consistency task.*

![Figure 46: Comparison with different models for temporal consistency task.](/images/2602.23152/img-050.png)
*Figure 46: Comparison with different models for temporal consistency task.*

![Figure 47: Comparison with different models for modal and spatial consistency task.](/images/2602.23152/img-051.png)
*Figure 47: Comparison with different models for modal and spatial consistency task.*

![Figure 48: Comparison with different models for modal and temporal consistency task.](/images/2602.23152/img-052.png)
*Figure 48: Comparison with different models for modal and temporal consistency task.*

![Figure 49: Comparison with different models for spatial and temporal consistency task.](/images/2602.23152/img-053.png)
*Figure 49: Comparison with different models for spatial and temporal consistency task.*


## 참고
- HF: https://huggingface.co/papers/2602.23152
- PDF: https://arxiv.org/pdf/2602.23152.pdf
