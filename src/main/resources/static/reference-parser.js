function parse(referenceStr) {
    var references = [];
    if (referenceStr.trim()) {
        referenceStr = referenceStr.replace(/(\s*)/g, "");
        var refArray = referenceStr.split("@").filter(ref => ref);
        var isValid = true;
        $.each(refArray, function (index, ref) {
            if (isNaN(ref)) {
                throw '참조 형식이 잘못되었습니다.';
            }
        });
        if (!isValid) {
            return;
        }
        references = refArray.map(i => Number(i));
    } else {
        references = []
    }
    return references;
}