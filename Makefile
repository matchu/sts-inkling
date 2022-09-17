.PHONY: watch
watch:
	cd theDefault && find . | entr mvn package