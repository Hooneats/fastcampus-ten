# GetFlow

### v1) release 브랜치 생성 후 추가적인 작업 필요한 경우
- dev 브랜치 생성
- feature/login 기능 구현
- feature/logout 기능 구현
- dev 작업 완료(feature/login , feature/logout 병합)
---
- release/1.0.0 브랜치 생성
- release/1.0.0 에서 추가 작업 생겨 release -> feature/find pw 기능 구현
- release/1.0.0 에 feature/find_pw 병합
---
- release 작업 완료 main 브랜치에 머지
- main 운영 배포 완료(가정)
---
- main 과의 싱크를 맞추기 위해 dev 에 main 머지

---

### v2) release 브랜치 생성 후 추가적인 작업 필요하지 않은 경우
- dev 브랜치 생성
- feature/login 기능 구현
- feature/logout 기능 구현
- dev 작업 완료(feature/login , feature/logout 병합)
---
- release/2.0.0 브랜치 생성
---
- release/2.0.0 을 main 브랜치에 머지
- main 운영 배포 완료(가정)
---
- main 과의 싱크를 맞추기 위해 dev 에 main 머지

---

### hotfix) Hotfix 가 필요한 경우
- main 브랜치에서 빠르게 처리할 hotfix 브랜치 생성
---
- hotfix 기능 구현
---
- hotfix 를 main 브랜치에 머지
- main 운영 배포 완료(가정)
---
- main 과의 싱크를 맞추기 위해 dev 에  main 머지
---
