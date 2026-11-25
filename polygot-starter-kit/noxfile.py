import nox

@nox.session(python=['3.11','3.12'])
def tests(s):
    s.run('uv','run','pytest')
