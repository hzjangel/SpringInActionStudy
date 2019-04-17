module.exports = {
    title: 'Spring学习',
    description: 'jQuery学习笔记',
    head: [
        ['link', { rel: 'icon', href: `/logo.png` }],
    ],
    markdown: {
        //显示行号
        // lineNumbers: true
    },
    themeConfig: {
        // 将会自动在每个页面的导航栏生成生成一个 GitHub 链接，以及在页面的底部生成一个 "Edit this page" 链接
        //假定是 GitHub. 同时也可以是一个完整的 GitLab URL
        repo: 'hzjanger/SpringInActionStudy',
        // 默认是 false, 设置为 true 来启用
        editLinks: true,
        // 假如文档不是放在仓库的根目录下：
        docsDir: 'docs',
        // 默认为 "Edit this page"
        editLinkText: '在 GitHub 上编辑此页',
        //每个文件最后一次 git 提交的 UNIX 时间戳(ms)，同时它将以合适的日期格式显示在每一页的底部
        lastUpdated: '上次更新',
        sidebar: [
            {
                title: 'Introduction',
                collapsable: false,
                children: [
                    '/'
                ]
            },
            {
                title: '第一部分 Spring核心',
                collapsable: false,
                children: [
                    ['chapter_01/', 'introduction'],
                    'chapter_01/one'
                ]

            },
            {
                title: '第五章 构建Spring Web应用程序',
                collapsable: false,
                children: [
                    ['chapter_05/', 'introduction'],
                    'chapter_05/Spring Mvc起步.md'
                ]
            }
        ]
    }
};
