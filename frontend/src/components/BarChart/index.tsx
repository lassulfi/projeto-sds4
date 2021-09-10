// @flow 
import axios from 'axios';
import React from 'react';
import { useEffect } from 'react';
import { useState } from 'react';
import Chart from 'react-apexcharts';
import { SaleSuccess } from 'types/sale';
import { round } from 'utils/format';
import { BASE_URL } from 'utils/requests';

type SeriesData = {
    name: string;
    data: number[];
}

type ChartData = {
    labels: {
        categories: string[]
    };
    series: SeriesData[];
}

const INITIAL_STATE: ChartData = {
    labels: {
        categories: []
    },
    series: [
        {
            name: '',
            data: []
        }
    ]
};

const BarChart = () => {
    const [chartData, setChartData] = useState<ChartData>(INITIAL_STATE);

    useEffect(() => {
        axios.get(`${BASE_URL}/sales/success`)
            .then(response => {
                const data = response.data as SaleSuccess[];
                const myLabels = data.map(x => x.name);
                const mySeries = data.map(x => round(100.0 * x.deals / x.visited, 1));

                setChartData({
                    labels: {
                        categories: myLabels
                    },
                    series: [
                        {
                            name: '% Sucesso',
                            data: mySeries,
                        }
                    ]
                })
            })
    }, [])

    const options = {
        plotOptions: {
            bar: {
                horizontal: true,
            }
        },
    };

    return (
        <Chart
            options={{
                ...options,
                xaxis: chartData.labels
            }}
            series={chartData.series}
            type="bar"
            height="240"
        />
    );
};

export default BarChart;