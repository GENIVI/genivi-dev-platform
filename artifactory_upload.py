import os

import argh
import requests
import yaml

del os.environ["http_proxy"]
del os.environ["https_proxy"]

@argh.dispatch_command
def main(art_output_url=None, target=None):
    if art_output_url is not None:
        os.environ["ART_OUTPUT_URL"] = art_output_url
    if target is not None:
        os.environ["TARGET"] = target

    conf = yaml.load(open(".bbtravis.yml"))
    to_upload = []
    for upload in conf['uploads']:
        destdir = upload.get('destdir')
        if destdir is None:
            print "warning: destdir is not defined!", upload
        destdir = destdir % os.environ
        files = upload.get('files')
        if files is None:
            print "warning: files is not defined!", upload
        for fn in files:
            fn = fn % os.environ
            if os.path.exists(fn):
                to_upload.append((fn, destdir))

    print "\n".join([str(x) for x in to_upload])
    for fn, destdir in to_upload:
        dest_url = os.path.join(destdir, os.path.basename(fn))
        f = open(fn, 'rb')
        res = requests.put(dest_url, data=f)
        print res.text
