# gdp-submodules
GDP setup using git submodules

--- 

This is *not* ready for public usage :-)

---- 

The project uses submodules to pull in dependencies

It is therefore recommended to :

```bash
git clone --recursive <thisrepo> -b <branch>
```
where branch is (for example) koelsch or minnowboard
(you must check which branches exist)

If you forgot the recursive option, try:
```
git checkout <branch>
git submodule init
git submodule update
```

If you do anything more advanced you probably need to study
information on git submodules - they are a bit special to
work with...
