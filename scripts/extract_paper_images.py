#!/usr/bin/env python3
"""
Download a paper PDF and extract images into public/images/<arxiv_id>/.
"""
from __future__ import annotations

import argparse
import glob
import os
import shutil
import subprocess
import tempfile
import urllib.request


def download(url: str, dest: str) -> None:
    with urllib.request.urlopen(url) as resp:  # nosec
        data = resp.read()
    with open(dest, "wb") as f:
        f.write(data)


def main() -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("--arxiv-id", required=True)
    ap.add_argument("--pdf-url", default="")
    ap.add_argument("--out-dir", default="")
    ap.add_argument("--force", action="store_true", help="Overwrite existing img-* files")
    args = ap.parse_args()

    arxiv_id = args.arxiv_id
    pdf_url = args.pdf_url or f"https://arxiv.org/pdf/{arxiv_id}.pdf"
    out_dir = args.out_dir or os.path.join(
        os.path.dirname(os.path.dirname(__file__)), "public", "images", arxiv_id
    )

    os.makedirs(out_dir, exist_ok=True)
    existing = glob.glob(os.path.join(out_dir, "img-*.png"))
    if existing and not args.force:
        print(f"[skip] images already exist in {out_dir}. Use --force to overwrite.")
        return 0
    for p in existing:
        os.remove(p)

    with tempfile.TemporaryDirectory() as tmp:
        pdf_path = os.path.join(tmp, f"{arxiv_id}.pdf")
        download(pdf_url, pdf_path)
        prefix = os.path.join(out_dir, "img")
        subprocess.check_call(["pdfimages", "-png", pdf_path, prefix])

    return 0


if __name__ == "__main__":
    raise SystemExit(main())
