#!/usr/bin/env python3
"""
Fetch top N papers from Hugging Face Papers page.
Outputs JSON to stdout.
"""
from __future__ import annotations

import argparse
import json
import re
import sys
import urllib.request
from xml.etree import ElementTree

ARXIV_API = "http://export.arxiv.org/api/query?id_list={}"  # nosec


def fetch_url(url: str) -> str:
    with urllib.request.urlopen(url) as resp:  # nosec
        return resp.read().decode("utf-8", errors="ignore")


def parse_arxiv_meta(arxiv_id: str) -> dict:
    xml = fetch_url(ARXIV_API.format(arxiv_id))
    root = ElementTree.fromstring(xml)
    ns = {"a": "http://www.w3.org/2005/Atom"}
    entry = root.find("a:entry", ns)
    if entry is None:
        return {"id": arxiv_id}
    title = (entry.findtext("a:title", default="", namespaces=ns) or "").strip()
    summary = (entry.findtext("a:summary", default="", namespaces=ns) or "").strip()
    authors = [
        (a.findtext("a:name", default="", namespaces=ns) or "").strip()
        for a in entry.findall("a:author", ns)
    ]
    published = (entry.findtext("a:published", default="", namespaces=ns) or "").strip()
    return {
        "id": arxiv_id,
        "title": re.sub(r"\s+", " ", title),
        "summary": re.sub(r"\s+", " ", summary),
        "authors": authors,
        "published": published,
    }


def main() -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("--source-url", required=True, help="HF papers page URL")
    ap.add_argument("--top", type=int, default=2, help="Top N papers")
    args = ap.parse_args()

    html = fetch_url(args.source_url)
    ids = re.findall(r"/papers/(\d{4}\.\d{5})", html)
    # preserve order, unique
    seen = set()
    ordered = []
    for pid in ids:
        if pid in seen:
            continue
        seen.add(pid)
        ordered.append(pid)
    top_ids = ordered[: args.top]

    results = []
    for pid in top_ids:
        meta = parse_arxiv_meta(pid)
        meta["hf_url"] = f"https://huggingface.co/papers/{pid}"
        meta["arxiv_url"] = f"https://arxiv.org/abs/{pid}"
        meta["pdf_url"] = f"https://arxiv.org/pdf/{pid}.pdf"
        results.append(meta)

    payload = {"source_url": args.source_url, "top": args.top, "papers": results}
    json.dump(payload, sys.stdout, ensure_ascii=False, indent=2)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
