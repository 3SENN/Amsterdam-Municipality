/** @type {import('tailwindcss').Config} */
module.exports = {

    content: [
        "./index.html",
        "./src/**/*.{vue,js,ts,jsx,tsx}"
    ],
    theme: {
        extend: {
            colors: {
                'municipality': {
                    25: "rgba(255,50,50,0.5)",
                    50: "#ff3232",
                    100: "#ff2828",
                    200: "#ff1e1e",
                    300: "#ff1414",
                    400: "#f60a0a",
                    500: "#ec0000",
                    600: "#e20000",
                    700: "#d80000",
                    800: "#ce0000",
                    900: "#c40000"
                },
                "subtext": {
                    "50": "#cad0cb",
                    "100": "#c0c6c1",
                    "200": "#b6bcb7",
                    "300": "#acb2ad",
                    "400": "#a2a8a3",
                    "500": "#989e99",
                    "600": "#8e948f",
                    "700": "#848a85",
                    "800": "#7a807b",
                    "900": "#707671"
                }
            },
            backgroundImage: {
                'dashboardBackground': "url('../img/dashboardBackground.svg')",
                'counting': "url('../img/levels/counting.png')",
                'quiz': "url('../img/levels/quiz.png')",
                'safari': "url('../img/levels/safari.jpg')",
                'helpTravel': "url('../img/levels/help.jpg')",
                'feedback': "url('../img/levels/feedback.png')",
                'finalBoss': "url('../img/levels/finalboss.jpg')"
            },
        },
    },
    variants: {
        extend: {},
    },
    plugins: []
}